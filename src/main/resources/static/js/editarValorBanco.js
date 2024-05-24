document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('form');

    form.addEventListener('submit', function (event) {
        var precoVenda = document.getElementById('precoVenda');
        var precoCusto = document.getElementById('precoCusto');

        //console.log('Depurar Antes:', { precoVenda: precoVenda.value, precoCusto: precoCusto.value });

        var precoVendaFormatado = precoVenda.value.replace(/\./g, '').replace(',', '.');
        var precoCustoFormatado = precoCusto.value.replace(/\./g, '').replace(',', '.');

        //console.log('Depurar Valor Formatado:', { precoVendaFormatado, precoCustoFormatado });
        //console.log('Depurar Tipo Inicial:', { tipoPrecoVenda: typeof precoVendaFormatado, tipoPrecoCusto: typeof precoCustoFormatado });

        var precoVendaDouble = parseFloat(precoVendaFormatado);
        var precoCustoDouble = parseFloat(precoCustoFormatado);

        if (isNaN(precoVendaDouble) || isNaN(precoCustoDouble)) {
            //console.error('Valores não convertidos corretamente:', { precoVendaFormatado, precoCustoFormatado });
            alert('Erro ao converter os valores. Por favor, revise os valores de preço.');
            event.preventDefault();  // Impede o envio do formulário em caso de erro
        } else {
            console.log('Depurar Após Conversão:', { precoVendaDouble, precoCustoDouble });
            console.log('Depurar Tipo Após Conversão:', { tipoPrecoVenda: typeof precoVendaDouble, tipoPrecoCusto: typeof precoCustoDouble });

            precoCusto.value = precoCustoDouble;
            precoVenda.value = precoVendaDouble;

            // Permite o envio do formulário
        }
    });
});
