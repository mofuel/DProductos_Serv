document.getElementById("selectMedicamento").addEventListener("change", function () {
    const horarioId = this.value;

    if (!horarioId) {
        Swal.fire({
            icon: 'warning',
            title: 'Aviso',
            text: 'Por favor selecciona un medicamento válido.',
            confirmButtonText: 'Aceptar'
        });
        return;
    }

    fetch("/entregamedicamento/verificar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [document.querySelector('meta[name="_csrf_header"]').getAttribute('content')]:
                document.querySelector('meta[name="_csrf"]').getAttribute('content')
        },
        body: JSON.stringify({ horarioId: parseInt(horarioId) })
    })
    .then(response => response.json())
    .then(data => {
        if (data.estado === "OK") {
            document.getElementById("simulacionEntrega").style.display = "block";
            document.getElementById("confirmacionEntrega").style.display = "none";
            document.getElementById("moverBtn").disabled = false;

            setTimeout(() => {
                document.getElementById("simulacionEntrega").style.display = "none";
                document.getElementById("confirmacionEntrega").style.display = "block";
            }, 3000);
        } else if (data.estado === "FUERA_DE_RANGO") {
            let mensajeAdicional = "";
            if (data.direccion === "temprano") {
                mensajeAdicional = `Te estás adelantando por <strong>${data.minutosDiferencia} minuto(s)</strong>.`;
            } else if (data.direccion === "tarde") {
                mensajeAdicional = `Te estás retrasando por <strong>${data.minutosDiferencia} minuto(s)</strong>.`;
            }

            Swal.fire({
                icon: 'info',
                title: 'Horario inválido',
                html: `
                    Este medicamento no puede ser entregado ahora.<br>
                    <strong>Fecha programada:</strong> ${data.fechaProgramada}<br>
                    <strong>Hora programada:</strong> ${data.horaProgramada}<br>
                    <strong>Diferencia:</strong> ${data.diferencia} ${data.direccion === 'tarde' ? 'después' : 'antes'} de lo programado.
                `,
                confirmButtonText: 'Aceptar'
            });

            document.getElementById("simulacionEntrega").style.display = "none";
            document.getElementById("confirmacionEntrega").style.display = "none";
            document.getElementById("moverBtn").disabled = true;
        } else {
            Swal.fire("Error", data.mensaje || "Error desconocido", "error");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        Swal.fire("Error", "Ocurrió un error al verificar el horario.", "error");
    });
});




function confirmarEntrega(confirmado) {
    const horarioId = document.getElementById("selectMedicamento").value;

    // Obtener el token CSRF
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // Enviar los datos a la ruta del controlador
    fetch('/entregamedicamento/registrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken  // Incluir el token CSRF en la cabecera
        },
        body: JSON.stringify({
            horarioId: horarioId,
            confirmado: confirmado
        })
    }).then(response => {
        if (response.ok) {
            // Usar SweetAlert2 para mostrar el mensaje de éxito
            Swal.fire({
                icon: 'success',
                title: '¡Entrega registrada!',
                text: 'El medicamento ha sido registrado correctamente.',
                confirmButtonText: 'Aceptar'
            }).then(() => {
                location.reload();  // Recargar la página después de la confirmación
            });
        } else {
            return response.text().then(text => {
                // Usar SweetAlert2 para mostrar el mensaje de error
                Swal.fire({
                    icon: 'error',
                    title: 'Error al guardar la entrega',
                    text: text,
                    confirmButtonText: 'Aceptar'
                });
            });
        }
    }).catch(error => {
        console.error("Error:", error);
        // Usar SweetAlert2 para mostrar el mensaje de error de comunicación
        Swal.fire({
            icon: 'error',
            title: 'Error de comunicación',
            text: 'Hubo un error al procesar la solicitud.',
            confirmButtonText: 'Aceptar'
        });
    });
}


const canvas = document.getElementById('mapa');
const ctx = canvas.getContext('2d');

const tileSize = 40;
const filas = 10;
const columnas = 10;

const mapa = [
  [1,1,1,1,1,1,1,1,1,1],
  [1,0,0,0,1,0,0,2,0,1],
  [1,0,1,0,1,0,1,1,0,1],
  [1,0,1,3,3,3,0,1,0,1],  // Agua en (3,3)-(3,5)
  [1,0,1,1,1,1,0,1,0,1],
  [1,0,0,0,0,1,0,1,0,1],
  [1,0,1,1,0,1,0,1,0,1],
  [1,0,0,1,0,0,0,0,0,1],
  [1,1,0,0,0,1,1,1,0,1],
  [1,1,1,1,1,1,1,1,1,1]
];


const inicio = {fila: 1, col: 1};
const destino = {fila: 7, col: 8};

// Referencias a los íconos HTML
const robotIcon = document.getElementById('robotIcon');
const personaIcon = document.getElementById('personaIcon');

// Posición inicial del robot en pixeles
let robotPos = {
  x: inicio.col * tileSize + tileSize / 2,
  y: inicio.fila * tileSize + tileSize / 2
};

let ruta = [];
let rutaIndex = 0;
const velocidad = 2;

function dibujarMapa() {
  // 1) Limpia el canvas
  ctx.clearRect(0, 0, canvas.width, canvas.height);

  // 2) Dibuja muros y agua/piso
  for (let fila = 0; fila < filas; fila++) {
    for (let col = 0; col < columnas; col++) {
      const tipo = mapa[fila][col];
      if (tipo === 1) {
        ctx.strokeStyle = '#FFFFFF';
        ctx.lineWidth = 6;
        ctx.strokeRect(col * tileSize, fila * tileSize, tileSize, tileSize);
      } else if (tipo === 3) {
        ctx.fillStyle = '#2196F3'; // Agua
        ctx.fillRect(col * tileSize, fila * tileSize, tileSize, tileSize);
      } else {
        ctx.fillStyle = '#1c1c1c'; // Piso libre
        ctx.fillRect(col * tileSize, fila * tileSize, tileSize, tileSize);
      }
    }
  }

  // 3) Elimina iconos anteriores
  const container = document.getElementById('contenedor');
  container.querySelectorAll('.map-icon').forEach(icon => icon.remove());

  // 4) Dibuja iconos de muebles (tipo 2)
  for (let fila = 0; fila < filas; fila++) {
    for (let col = 0; col < columnas; col++) {
      if (mapa[fila][col] === 2) {
        // Calcula coordenadas del centro de la celda
        const x = col * tileSize + tileSize / 2;
        const y = fila * tileSize + tileSize / 2;
        // Añádelo al contenedor, no al body
        colocarIconoFontAwesome(x, y, 'fa-couch');
      }
    }
  }
}


function colocarIconoFontAwesome(x, y, iconClass) {
  const container = document.getElementById('contenedor');
  const icon = document.createElement('i');
  icon.className = `fas ${iconClass} map-icon`;
  icon.style.left = `${x}px`;  // centro X
  icon.style.top  = `${y}px`;  // centro Y
  container.appendChild(icon);
}






// Función para posicionar íconos HTML
function posicionarIcono(elemento, x, y) {
  // Se posiciona de modo que el centro del ícono quede en (x,y)
  const size = 36; // font-size px del icono
  elemento.style.left = (x - size / 2) + 'px';
  elemento.style.top = (y - size / 2) + 'px';
}

function dibujarIconos() {
  // Persona fija en el destino
  const xDest = destino.col * tileSize + tileSize / 2;
  const yDest = destino.fila * tileSize + tileSize / 2;
  posicionarIcono(personaIcon, xDest, yDest);

  // Robot en su posición actual
  posicionarIcono(robotIcon, robotPos.x, robotPos.y);
}

// Algoritmo A* (igual que el tuyo)
function encontrarRuta(inicio, destino) {
  const abiertos = [];
  const cerrados = new Set();
  const cameFrom = new Map();

  function nodoKey(n) { return n.fila + ',' + n.col; }
  function heuristica(a, b) {
    return Math.abs(a.fila - b.fila) + Math.abs(a.col - b.col);
  }
  function vecinos(nodo) {
    const deltas = [
      {df: -1, dc: 0},
      {df: 1, dc: 0},
      {df: 0, dc: -1},
      {df: 0, dc: 1}
    ];
    const resultado = [];
    for(const d of deltas) {
      const nf = nodo.fila + d.df;
      const nc = nodo.col + d.dc;
      if(nf >= 0 && nf < filas && nc >= 0 && nc < columnas && mapa[nf][nc] === 0) {
        resultado.push({fila: nf, col: nc});
      }
    }
    return resultado;
  }

  const gScore = new Map();
  const fScore = new Map();

  gScore.set(nodoKey(inicio), 0);
  fScore.set(nodoKey(inicio), heuristica(inicio, destino));
  abiertos.push({...inicio, f: fScore.get(nodoKey(inicio))});

  while(abiertos.length > 0) {
    abiertos.sort((a,b) => a.f - b.f);
    let current = abiertos.shift();

    if(current.fila === destino.fila && current.col === destino.col) {
      const path = [];
      let curKey = nodoKey(current);
      while(cameFrom.has(curKey)) {
        path.push(current);
        current = cameFrom.get(curKey);
        curKey = nodoKey(current);
      }
      path.push(inicio);
      return path.reverse();
    }

    cerrados.add(nodoKey(current));

    for(const vecino of vecinos(current)) {
      if(cerrados.has(nodoKey(vecino))) continue;
      const tentativeG = gScore.get(nodoKey(current)) + 1;

      const vecinoG = gScore.get(nodoKey(vecino));
      if(vecinoG === undefined || tentativeG < vecinoG) {
        cameFrom.set(nodoKey(vecino), current);
        gScore.set(nodoKey(vecino), tentativeG);
        fScore.set(nodoKey(vecino), tentativeG + heuristica(vecino, destino));

        if(!abiertos.some(n => n.fila === vecino.fila && n.col === vecino.col)) {
          abiertos.push({...vecino, f: fScore.get(nodoKey(vecino))});
        }
      }
    }
  }

  return [];
}

function animarMovimiento(callback) {
  if(ruta.length === 0 || rutaIndex >= ruta.length) {
    if (callback) callback();
    return;
  }

  const nodoDestino = ruta[rutaIndex];
  const xDestino = nodoDestino.col * tileSize + tileSize / 2;
  const yDestino = nodoDestino.fila * tileSize + tileSize / 2;

  const dx = xDestino - robotPos.x;
  const dy = yDestino - robotPos.y;
  const distancia = Math.sqrt(dx*dx + dy*dy);

  if(distancia < velocidad) {
    robotPos.x = xDestino;
    robotPos.y = yDestino;
    rutaIndex++;
  } else {
    robotPos.x += (dx / distancia) * velocidad;
    robotPos.y += (dy / distancia) * velocidad;
  }

  dibujarMapa();
  dibujarRuta(ruta);
  dibujarIconos();

  if(rutaIndex < ruta.length) {
    requestAnimationFrame(() => animarMovimiento(callback));
  } else if(callback) {
    callback();
  }
}



// Al cargar
dibujarMapa();
dibujarIconos();

function dibujarRuta(ruta) {
  // Dibuja rectángulos verdes en cada nodo de la ruta
  ctx.fillStyle = 'rgba(57, 255, 20, 0.8)';
  for (const nodo of ruta) {
    ctx.fillRect(nodo.col * tileSize, nodo.fila * tileSize, tileSize, tileSize);
  }
}

const boton = document.getElementById('moverBtn');

boton.addEventListener('click', () => {
  boton.disabled = true; // deshabilita botón para evitar clicks múltiples

  Swal.fire({
    title: 'Calculando ruta óptima',
    html: 'Espere <b></b> segundos...',
    timer: 3000,
    timerProgressBar: true,
    didOpen: () => {
      Swal.showLoading();
      const b = Swal.getHtmlContainer().querySelector('b');
      let timerInterval = setInterval(() => {
        b.textContent = Math.ceil(Swal.getTimerLeft() / 1000);
      }, 100);
      setTimeout(() => clearInterval(timerInterval), 3000);
    }
  }).then(() => {
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    // Supongamos que 'inicio' y 'destino' tienen la estructura { row: ..., col: ... }
    // 'usuario' es un objeto con datos del usuario actual, debes tenerlo definido antes.
    // 'ruta' es un array con los pasos calculados [{ row: ..., col: ... }, ...]
    // 'distanciaCalculada', 'nodosExplorados' y 'tiempoCalculo' también deben estar definidos.

    ruta = encontrarRuta(inicio, destino);

    // 2. Calcular distancia (suponiendo que cada paso tiene distancia 1)
    let distanciaCalculada = 0;
    for (let i = 1; i < ruta.length; i++) {
      // Distancia entre pasos consecutivos, aquí es 1 por movimiento ortogonal
      distanciaCalculada += 1;
    }

    // 3. Otros datos (por ejemplo, nodos explorados, tiempo, etc.)
    // Puedes agregar variables para medir tiempo si quieres
    const nodosExplorados = 'N/A';  // o calcular según tu lógica
    const tiempoCalculo = 'N/A';    // o medir con performance.now()

    // Construir el objeto RutaDto a enviar
    const rutaDto = {
        startRow: inicio.fila,    // o el valor correcto que tengas
        startCol: inicio.col,
        endRow: destino.fila,
        endCol: destino.col,
        totalDistance: distanciaCalculada, // tu variable con la distancia
        nodesExplored: null,       // o número real si lo tienes
        computeTimeMs: null        // o número real si lo tienes
    };

    fetch('/robot/mover', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
      },
      body: JSON.stringify(rutaDto)
    })
    .then(res => {
      if (!res.ok) {
        throw new Error('Error en el servidor');
      }
      return res.json();
    })
    .then(data => {
      console.log('Respuesta del backend:', data);

      // Aquí puedes usar la ruta que devolvió el backend o la que tengas calculada
      // Por ejemplo, ruta = data.ruta;
      // O usar tu función local para encontrar ruta si quieres:
      // ruta = encontrarRuta(inicio, destino);

      if (ruta.length === 0) {
        Swal.fire('No se encontró ruta', '', 'error');
        boton.disabled = false;
        return;
      }

      dibujarMapa();
      dibujarRuta(ruta);
      dibujarIconos();
      rutaIndex = 1;

      animarMovimiento(() => {
        boton.disabled = false;
        Swal.fire({
          icon: 'success',
          title: 'El robot ya está aquí',
          timer: 2000,
          showConfirmButton: false
        });
      });
    })
    .catch(error => {
      console.error('Error:', error.message);
      Swal.fire('Error', 'No se pudo conectar con el servidor', 'error');
      boton.disabled = false;
    });
  });
});

function confirmarEntrega(confirmado) {
    const horarioId = document.getElementById("selectMedicamento").value;

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch('/entregamedicamento/registrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({ horarioId: horarioId, confirmado: confirmado })
    })
    .then(response => {
        if (!response.ok) {
            return response.text().then(text => { throw new Error(text); });
        }
        return response.text();
    })
    .then(data => {
        if (data === "OK") {
            Swal.fire("Éxito", "Entrega registrada correctamente", "success");
        } else {
            Swal.fire("Aviso", data, "warning");
        }
    })
    .catch(error => {
        console.error("Error:", error);
        Swal.fire("Error", "No se pudo registrar la entrega. " + error.message, "error");
    });
}


