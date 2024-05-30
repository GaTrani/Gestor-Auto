$(document).ready(function () {
    var tipoDesconto = 'R$'; // Default: tipo de desconto em R$
    var descontoAnteriorEmReais = 0; // Valor do desconto anterior em reais

    // Evento para botões de tipo de desconto
    $('.btn-group button').click(function () {
        $('.btn-group button').removeClass('btn-primary').addClass('btn-secondary');
        $(this).removeClass('btn-secondary').addClass('btn-primary');

        tipoDesconto = $(this).text();
        ajustarCampoDesconto();
        aplicarDesconto();
    });

    // Evento para campo de desconto
    $('#desconto').on('input', function () {
        aplicarDesconto();
    });

    // Função para ajustar o campo de desconto conforme o tipo de desconto
    function ajustarCampoDesconto() {
        if (tipoDesconto === 'R$') {
            $('#desconto').val(descontoAnteriorEmReais.toFixed(2));
            $('#desconto').attr('step', '0.01');
            $('#desconto').attr('max', calcularTotalProdutos());
        } else {
            var totalProdutos = parseFloat(calcularTotalProdutos());
            var descontoPercentual = (descontoAnteriorEmReais / totalProdutos) * 100;
            $('#desconto').val(descontoPercentual.toFixed(2));
            $('#desconto').attr('step', '0.01');
            $('#desconto').attr('max', '100');
        }
    }

    // Função para aplicar o desconto
    function aplicarDesconto() {
        var totalProdutos = parseFloat(calcularTotalProdutos());
        var descontoValor = parseFloat($('#desconto').val().replace(',', '.')) || 0; // Replace comma with dot for decimal values

        var valorComDesconto;

        if (tipoDesconto === '%') {
            if (descontoValor > 100) {
                descontoValor = 100;
                $('#desconto').val(100);
            }
            descontoAnteriorEmReais = (totalProdutos * (descontoValor / 100));
            valorComDesconto = totalProdutos - descontoAnteriorEmReais;
        } else {
            if (descontoValor > totalProdutos) {
                descontoValor = totalProdutos;
                $('#desconto').val(totalProdutos.toFixed(2));
            }
            descontoAnteriorEmReais = descontoValor;
            valorComDesconto = totalProdutos - descontoAnteriorEmReais;
        }

        $('#valorTotalNota').val(totalProdutos.toFixed(2));
        $('#valorTotalComDesconto').val(valorComDesconto.toFixed(2));
    }

    // Evento para selecionar forma de pagamento
    $('.btn-group.btn-group-toggle button').click(function () {
        $('.btn-group.btn-group-toggle button').removeClass('btn-primary').addClass('btn-secondary');
        $(this).removeClass('btn-secondary').addClass('btn-primary');

        var formaPagamento = $(this).text();
        $('#formaPagamento').val(formaPagamento);
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
