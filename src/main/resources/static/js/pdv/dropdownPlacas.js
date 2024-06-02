function carregarVeiculos() {
    var clienteId = $('#clienteId').val();
    var pdv = $('#pdvId').val();
    if (clienteId) {
        $.ajax({
            url: '/pdv/cliente/' + clienteId + '/veiculos',
            method: 'GET',
            success: function (data) {
                console.log('id', clienteId, 'os dados: ', data, 'pdv? ', pdv)
                var veiculoSelect = $('#veiculo');
                veiculoSelect.empty();
                veiculoSelect.append('<option value="">Selecione um veículo</option>');
                data.forEach(function (veiculo) {
                    veiculoSelect.append('<option value="' + veiculo + '">' + veiculo + '</option>');
                });
                // Se o veículo do PDV estiver definido, selecione-o
                if (pdv.veiculo != null) {
                    console.log('tentou')
                    veiculoSelect.val(pdv.veiculo);
                }
            },
            error: function (xhr, status, error) {
                console.error('Erro ao carregar veículos:', error);
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

// Chame a função carregarVeiculos() quando o documento estiver pronto
$(document).ready(function() {
    carregarVeiculos();
});
