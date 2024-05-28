document.addEventListener('DOMContentLoaded', function () {
    console.log('Desconto formatter loaded');
    var desconto = document.getElementById('desconto');

    function formatarValor(event) {
        var input = event.target;
        var value = input.value;

        // Remove todos os caracteres que não são dígitos
        value = value.replace(/\D/g, '');

        // Adiciona vírgula para casas decimais
        value = (value / 100).toFixed(2);

        // Divide em parte inteira e decimal
        var parts = value.split('.');

        // Adiciona separador de milhar
        parts[0] = parts[0].split(/(?=(?:...)*$)/).join('.');

        input.value = parts.join(',');
    }

    desconto.addEventListener('input', formatarValor);
});