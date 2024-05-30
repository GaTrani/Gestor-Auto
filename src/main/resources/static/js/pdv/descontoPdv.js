$(document).ready(function () {
    var tipoDesconto = 'R$'; // Default: tipo de desconto em R$
    var descontoAnteriorEmReais = 0; // Valor do desconto anterior em reais
    var descontoEmReais = 0; // Novo valor do desconto sempre em reais

    // Evento para botões de tipo de desconto
    $('.btn-desconto-group button').click(function () {
        if ($(this).hasClass('btn-primary')) {
            // Deselecionar o botão se ele já estiver selecionado
            $(this).removeClass('btn-primary').addClass('btn-secondary');
            tipoDesconto = null;
            $('#desconto').val('');
            descontoAnteriorEmReais = 0;
        } else {
            $('.btn-desconto-group button').removeClass('btn-primary').addClass('btn-secondary');
            $(this).removeClass('btn-secondary').addClass('btn-primary');
            tipoDesconto = $(this).text();
        }
        ajustarCampoDesconto();
        aplicarDesconto();
    });

    // Evento para campo de desconto
    $('#desconto').on('input', function () {
        aplicarDesconto();
    });

    // Função para ajustar o campo de desconto conforme o tipo de desconto
    function ajustarCampoDesconto() {
        var totalProdutos = parseFloat(calcularTotalProdutos()) || 0;
        if (tipoDesconto === 'R$') {
            $('#desconto').val(descontoAnteriorEmReais.toFixed(2));
            $('#desconto').attr('step', '0.01');
            $('#desconto').attr('max', totalProdutos);
        } else if (tipoDesconto === '%') {
            if (totalProdutos > 0) {
                var descontoPercentual = (descontoAnteriorEmReais / totalProdutos) * 100;
                $('#desconto').val(descontoPercentual.toFixed(2));
            } else {
                $('#desconto').val('0.00');
            }
            $('#desconto').attr('step', '0.01');
            $('#desconto').attr('max', '100');
        } else {
            $('#desconto').val('');
        }
    }

    // Função para aplicar o desconto
    function aplicarDesconto() {
    var totalProdutos = parseFloat(calcularTotalProdutos()) || 0;
    var descontoValor = parseFloat($('#desconto').val().replace(',', '.')) || 0; // Replace comma with dot for decimal values
    console.log('1totalProdutos: ', totalProdutos, 'descontoValor: ', descontoValor)
    $('#desconto').val(descontoValor.toFixed(2));
    var valorComDesconto;

    if (totalProdutos === 0) {
        valorComDesconto = 0;
        descontoAnteriorEmReais = 0;
    } else if (tipoDesconto === '%') {
        if (descontoValor > 100) {
            descontoValor = 100;
            $('#desconto').val(100);
        }
        descontoAnteriorEmReais = (totalProdutos * (descontoValor / 100));

        descontoEmReais = descontoAnteriorEmReais;
        console.log('DESCONTO EM REAIS SALVAR: ', descontoEmReais)
        $('#descontoEmReais').val(descontoEmReais);
        valorComDesconto = totalProdutos - descontoAnteriorEmReais;
    } else if (tipoDesconto === 'R$') {
        if (descontoValor > totalProdutos) {
            descontoValor = totalProdutos;
            $('#desconto').val(totalProdutos.toFixed(2));
        }
        descontoEmReais = descontoValor;
        console.log('DESCONTO EM REAIS SALVAR2: ', descontoEmReais)
        $('#descontoEmReais').val(descontoEmReais);
        descontoAnteriorEmReais = descontoValor;
        valorComDesconto = totalProdutos - descontoAnteriorEmReais;
    } else {
        descontoAnteriorEmReais = 0;
        valorComDesconto = totalProdutos;
    }
    console.log('2totalProdutos: ', totalProdutos, 'descontoValor: ', descontoValor)
    $('#valorTotalNota').val(totalProdutos.toFixed(2));
    $('#valorTotalComDesconto').val(valorComDesconto.toFixed(2));
}


    // Evento para selecionar forma de pagamento
    $('.btn-pagamento-group button').click(function () {
        if ($(this).hasClass('btn-primary')) {
            // Deselecionar o botão se ele já estiver selecionado
            $(this).removeClass('btn-primary').addClass('btn-secondary');
            $('#formaPagamento').val('');
        } else {
            $('.btn-pagamento-group button').removeClass('btn-primary').addClass('btn-secondary');
            $(this).removeClass('btn-secondary').addClass('btn-primary');
            var formaPagamento = $(this).text();
            $('#formaPagamento').val(formaPagamento);
        }
    });

    // Função para calcular total dos produtos (mantida do seu código original)
    function calcularTotalProdutos() {
        let total = 0;
        produtosAdicionados.forEach(function (produto) {
            total += produto.precoVenda * produto.quantidade;
        });
        return total.toFixed(2); // Retorna o total com duas casas decimais
    }

    // Chamar aplicarDesconto para inicializar os valores corretos
    aplicarDesconto();
});
