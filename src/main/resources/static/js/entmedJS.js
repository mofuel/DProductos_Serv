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
