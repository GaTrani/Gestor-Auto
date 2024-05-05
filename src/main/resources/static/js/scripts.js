function atualizarModelos() {
    var marcaSelecionada = parseInt(document.getElementById("marca").value);
    console.log("marca.." + marcaSelecionada)
    var modelosSelecionado = document.getElementById("modelo");

    // Limpa as opções atuais do select de modelos
    modelosSelecionado.innerHTML = '<option value="">Selecione o modelo</option>';

    // Fazer uma requisição AJAX para obter os modelos de carro correspondentes à marca selecionada
    fetch('/buscarPorMarca?marca=' + marcaSelecionada)
        .then(response => response.json())
        .then(data => {
            data.forEach(function (modelo) {
                var option = document.createElement("option");
                option.value = modelo.id;
                option.text = modelo.modelo;
                modelosSelecionado.appendChild(option);
            });
        })
        .catch(error => console.error('Erro ao obter modelos de carro:', error));
}
