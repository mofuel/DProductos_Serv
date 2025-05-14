document.addEventListener('DOMContentLoaded', function() {
        // Manejo de los botones de editar
        var editButtons = document.querySelectorAll('[data-bs-toggle="modal"][data-bs-target="#editHorarioModal"]');
        editButtons.forEach(function(button) {
            button.addEventListener('click', function() {
                var horarioId = button.getAttribute('data-id');
                var medicamentoId = button.getAttribute('data-medicamento');
                var hora = button.getAttribute('data-hora');
                var fecha = button.getAttribute('data-fecha');
                var frecuencia = button.getAttribute('data-frecuencia');

                // Rellenamos los campos del formulario en el modal de editar
                document.getElementById('modalHorarioId').value = horarioId;
                document.getElementById('modalMedicamentoSelect').value = medicamentoId;
                document.getElementById('modalHora').value = hora;
                document.getElementById('modalFecha').value = fecha;
                document.getElementById('modalFrecuencia').value = frecuencia;

                // Actualizamos la acción del formulario
                var form = document.querySelector('#editHorarioModal form');
                form.action = '/horarios/editar/' + horarioId;
            });
        });

        // Manejo de los botones de agregar
        var addButton = document.querySelector('[data-bs-toggle="modal"][data-bs-target="#addHorarioModal"]');
        if (addButton) {
            addButton.addEventListener('click', function() {
                // Limpiamos los campos del formulario para agregar un nuevo horario
                document.getElementById('modalMedicamentoSelect').value = '';
                document.getElementById('modalHora').value = '';
                document.getElementById('modalFecha').value = '';
                document.getElementById('modalFrecuencia').value = '';

                // Opcionalmente, puedes actualizar la acción del formulario
                var form = document.querySelector('#addHorarioModal form');
                form.action = '/horarios/guardar'; // Acción para agregar un horario
            });
        }
    });