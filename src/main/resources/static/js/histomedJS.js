document.getElementById("selectMedicamento").addEventListener("change", function () {
        document.getElementById("simulacionEntrega").style.display = "block";
        document.getElementById("confirmacionEntrega").style.display = "none";

        // Simula carga por 3 segundos
        setTimeout(() => {
            document.getElementById("simulacionEntrega").style.display = "none";
            document.getElementById("confirmacionEntrega").style.display = "block";
        }, 3000);
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
                alert("Registro de entrega guardado.");
                location.reload();
            } else {
                return response.text().then(text => {
                    alert("Hubo un error al guardar la entrega: " + text);  // Muestra el error devuelto por el backend
                });
            }
        }).catch(error => {
            console.error("Error:", error);
            alert("Hubo un error de comunicación.");
        });
    }