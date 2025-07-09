let videollamadaId = null;
let estadoConversacion = "inicio";


function enviarMensaje() {
    const input = document.getElementById("mensajeInput");
    const mensaje = input.value.trim();
    const chatBody = document.getElementById("chatBody");

    if (mensaje !== "") {
        // Mostrar mensaje del usuario
        const divUser = document.createElement("div");
        divUser.classList.add("chat-message", "user");
        divUser.innerHTML = `<div class="message">${mensaje}</div>`;
        chatBody.appendChild(divUser);

        const mensajeLower = mensaje.toLowerCase();
        let respuestaTexto = "";

        // Flujo conversacional con estado
        switch (estadoConversacion) {
            case "inicio":
                if (mensajeLower.includes("hola") || mensajeLower.includes("buenos")) {
                    respuestaTexto = "Hola, ¿en qué puedo ayudarte hoy?";
                } else if (
                    mensajeLower.includes("dolor") ||
                    mensajeLower.includes("me siento mal") ||
                    mensajeLower.includes("enfermo") ||
                    mensajeLower.includes("síntomas")
                ) {
                    respuestaTexto = "Entiendo. ¿Qué síntomas estás presentando?";
                    estadoConversacion = "esperando_sintomas";
                } else {
                    respuestaTexto = "¿Podrías decirme qué síntomas tienes?";
                }
                break;

            case "esperando_sintomas":
                if (mensajeLower.includes("fiebre") || mensajeLower.includes("temperatura")) {
                    respuestaTexto = "¿La fiebre es constante o intermitente? ¿Tienes escalofríos?";
                    estadoConversacion = "detalle_fiebre";
                } else if (mensajeLower.includes("dolor de cabeza")) {
                    respuestaTexto = "¿Has tenido náuseas o visión borrosa?";
                    estadoConversacion = "detalle_cabeza";
                } else if (mensajeLower.includes("estómago") || mensajeLower.includes("diarrea")) {
                    respuestaTexto = "¿El dolor es después de comer o permanente?";
                    estadoConversacion = "detalle_estomago";
                } else {
                    respuestaTexto = "Gracias por compartirlo. ¿Tienes otros síntomas?";
                }
                break;

            case "detalle_fiebre":
                respuestaTexto = "Gracias por la información. Te recomendaría mantenerte hidratado y tomar paracetamol si la fiebre persiste.";
                estadoConversacion = "final";
                break;

            case "detalle_cabeza":
                respuestaTexto = "Podría tratarse de migraña. Si el dolor persiste, es importante evaluarlo en consulta.";
                estadoConversacion = "final";
                break;

            case "detalle_estomago":
                respuestaTexto = "Podría ser un problema digestivo leve. Si el dolor continúa o hay vómitos, deberías acudir a un centro médico.";
                estadoConversacion = "final";
                break;

            case "final":
                respuestaTexto = "¿Tienes alguna otra duda o síntoma que desees comentar?";
                break;

            default:
                respuestaTexto = "Disculpa, ¿puedes repetir tu síntoma?";
                estadoConversacion = "inicio";
        }

        // Mostrar respuesta del "médico"
        setTimeout(() => {
            const respuesta = document.createElement("div");
            respuesta.classList.add("chat-message", "doctor");
            respuesta.innerHTML = `<div class="message">${respuestaTexto}</div>`;
            chatBody.appendChild(respuesta);
            chatBody.scrollTop = chatBody.scrollHeight;
        }, 1500);

        input.value = "";
        chatBody.scrollTop = chatBody.scrollHeight;
    }
}



// Cargar médico al iniciar página
window.onload = () => {
const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch("/medico/disponible")
        .then(resp => resp.json())
        .then(medico => {
            document.getElementById("doctorNombre").textContent = medico.nombre;
            document.getElementById("doctorEspecialidad").textContent = medico.especialidad;
            document.getElementById("doctorFoto").src = medico.foto;
        }).catch(err => {
            console.warn("No se pudo cargar el médico disponible.");
        });
};

function iniciarVideollamada() {
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');

    fetch("/videollamada/iniciar", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken
        },
        body: JSON.stringify({})
    })
    .then(resp => {
        if (!resp.ok) throw new Error("Error al iniciar videollamada");
        return resp.json();
    })
    .then(data => {
        console.log("Videollamada iniciada con ID:", data.id);
        videollamadaId = data.id; // ⚠️ AQUÍ SE GUARDA EL ID

        // Activar botones
        document.getElementById("mensajeInput").disabled = false;
        document.getElementById("btnEnviar").disabled = false;
        document.getElementById("btnFinalizar").disabled = false;
        document.getElementById("btnIniciar").disabled = true;
    })
    .catch(err => {
        console.error("Error:", err);
        Swal.fire("Error", "No se pudo iniciar la videollamada", "error");
    });
}





function finalizarVideollamada() {
    if (!videollamadaId) {
        alert("No hay videollamada activa.");
        return;
    }

    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    fetch(`/videollamada/finalizar/${videollamadaId}`, {
        method: "PUT",
        headers: {
            [csrfHeader]: csrfToken
        }
    }).then(resp => {
        if (!resp.ok) throw new Error("Error al finalizar videollamada");
        return resp.json();
    }).then(data => {
        Swal.fire("Videollamada finalizada", `Duración: ${data.duracion} minutos`, "success");

        const chatBody = document.getElementById("chatBody");
        const finalMsg = document.createElement("div");
        finalMsg.classList.add("chat-message", "doctor");
        finalMsg.innerHTML = `<div class="message">📞 Videollamada finalizada. Duración: ${data.duracion} minutos.</div>`;
        chatBody.appendChild(finalMsg);
        chatBody.scrollTop = chatBody.scrollHeight;

        // Desactivar campos del chat
        document.getElementById("btnFinalizar").disabled = true;
        document.getElementById("mensajeInput").disabled = true;
        document.getElementById("btnEnviar").disabled = true;

        // ✅ Reactivar el botón de llamada
        document.getElementById("btnIniciar").disabled = false;

        // ✅ Resetear ID para evitar errores en una nueva llamada
        videollamadaId = null;
    }).catch(err => {
        console.error("Error al finalizar videollamada:", err);
        Swal.fire("Error", "No se pudo finalizar la videollamada", "error");
    });
}

