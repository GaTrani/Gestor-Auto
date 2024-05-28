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

$(document).ready(function() {
    carregarVeiculos();
});