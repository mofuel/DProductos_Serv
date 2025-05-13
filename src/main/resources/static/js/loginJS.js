  function openModal() {
    document.getElementById('modalRecuperar').style.display = 'block';
  }

  function closeModal() {
    document.getElementById('modalRecuperar').style.display = 'none';
  }

  // Cierra el modal al hacer clic fuera
  window.onclick = function(event) {
    const modal = document.getElementById('modalRecuperar');
    if (event.target == modal) {
      closeModal();
    }
  }