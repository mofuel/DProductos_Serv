document.addEventListener('DOMContentLoaded', () => {
    const editButtons = document.querySelectorAll('[data-bs-target="#editHorarioModal"]');

    editButtons.forEach(button => {
        button.addEventListener('click', () => {
            const id = button.getAttribute('data-id');
            const medicamento = button.getAttribute('data-medicamento');
            const hora = button.getAttribute('data-hora');
            const fecha = button.getAttribute('data-fecha');
            const frecuencia = button.getAttribute('data-frecuencia');

            // Rellenar los campos del modal
            document.getElementById('modalHorarioId').value = id;
            document.getElementById('modalMedicamentoSelect').value = medicamento;
            document.getElementById('modalHora').value = hora;
            document.getElementById('modalFecha').value = fecha;
            document.getElementById('modalFrecuencia').value = frecuencia;

            // Actualizar acción del formulario
            const form = document.querySelector('#editHorarioModal form');
            form.action = '/horarios/editar/' + id;
        });
    });
});
