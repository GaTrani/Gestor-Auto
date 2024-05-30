function carregarVeiculos() {
    var clienteId = $('#clienteId').val();
    if (clienteId) {
        $.ajax({
            url: '/pdv/cliente/' + clienteId + '/veiculos',
            method: 'GET',
            success: function (data) {
                var veiculoSelect = $('#veiculo');
                veiculoSelect.empty();
                veiculoSelect.append('<option value="">Selecione um veículo</option>');
                data.forEach(function (veiculo) {
                    veiculoSelect.append('<option value="' + veiculo + '">' + veiculo + '</option>');
                });
            }
        });
    } else {
        $('#veiculo').empty().append('<option value="">Selecione um veículo</option>');
    }
}
// Chamada da função quando um cliente é selecionado
$('#clienteId').change(function() {
    carregarVeiculos();
});

// Chamada da função quando o dropdown de veículos é clicado
$('#veiculo').click(function() {
    carregarVeiculos();
});

/* $(document).ready(function() {
    carregarVeiculos();
}); */