document.addEventListener('DOMContentLoaded', function () {
    var form = document.getElementById('form');
    var tabelaProdutosPDVVendas = document.getElementById('tabelaProdutosPDVVendas');

    form.addEventListener('submit', function (event) {
        var produtos = [];
        var rows = tabelaProdutosPDVVendas.querySelectorAll('tbody tr');

        rows.forEach(function (row) {
            var produto = {
                idProduto: row.querySelector('.idProduto').textContent,
                produto: row.querySelector('.nomeProduto').textContent,
                quantidade: row.querySelector('.quantidadeProduto').textContent,
                valorUnitario: row.querySelector('.valorUnitarioProduto').textContent.replace(/\./g, '').replace(',', '.'),
                total: row.querySelector('.totalProduto').textContent.replace(/\./g, '').replace(',', '.')
            };
            produtos.push(produto);
        });

        var inputProdutos = document.getElementById('produtos');
        inputProdutos.value = JSON.stringify(produtos);
    });
});
