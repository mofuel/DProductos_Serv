var editButtons = document.querySelectorAll('[data-bs-toggle="modal"]');
    editButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            var medicamentoId = button.getAttribute('data-id');
            var medicamentoNombre = button.getAttribute('data-nombre');
            var medicamentoDosis = button.getAttribute('data-dosis');

            // Rellenamos los campos del formulario en el modal
            document.getElementById('modalNombre').value = medicamentoNombre;
            document.getElementById('modalDosis').value = medicamentoDosis;

            // Actualizar el formulario del modal con el id del medicamento
            var modalForm = document.querySelector('#editModal form');
            modalForm.action = '/medicamentos/editar/' + medicamentoId;
        });
    });