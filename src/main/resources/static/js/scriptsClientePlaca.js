function adicionarPlaca() {
    var divPlacas = document.getElementById('placas');
    var novoCampo = document.createElement('div');
    novoCampo.classList.add('form-group');
    novoCampo.innerHTML = '<input type="text" class="form-control input-pequeno" name="placa" placeholder="Nova Placa">';
    divPlacas.appendChild(novoCampo);
}
