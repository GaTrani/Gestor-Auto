document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('form');

    form.addEventListener('submit', function (event) {
        var desconto = document.getElementById('desconto');

        // Console log for debugging before conversion
        console.log('Depurar Antes:', { desconto: desconto.value });

        var descontoFormatado = desconto.value.replace(/\./g, '').replace(',', '.');

        // Console log for formatted value
        console.log('Depurar Valor Formatado:', { descontoFormatado });

        var descontoDouble = parseFloat(descontoFormatado);

        if (isNaN(descontoDouble)) {
            console.error('Valor não convertido corretamente:', { descontoFormatado });
            alert('Erro ao converter o valor de desconto. Por favor, revise o valor do desconto.');
            event.preventDefault();  // Impede o envio do formulário em caso de erro
        } else {
            console.log('Depurar Após Conversão:', { descontoDouble });
            console.log('Depurar Tipo Após Conversão:', { tipoDesconto: typeof descontoDouble });

            desconto.value = descontoDouble;

            // Permite o envio do formulário
        }
    });
});
