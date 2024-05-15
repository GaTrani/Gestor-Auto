// Lista para armazenar os produtos adicionados
var produtosAdicionados = [];

// Evento de clique no botão "Adicionar"
$('#btnAdicionar').on('click', function() {
    var produtoSelecionado = $('#inputBusca').val();
    if (produtoSelecionado) {
        // Adicionar o produto à lista de produtos adicionados
        produtosAdicionados.push(produtoSelecionado);
        // Limpar o campo de busca
        $('#inputBusca').val('');
        // Exibir os produtos adicionados na tabela
        atualizarTabelaProdutos();
    } else {
        console.error('Erro ao capturar dados para adicionar produto.');
    }
});

// Função para atualizar a tabela de produtos
function atualizarTabelaProdutos() {
    // Limpar a tabela antes de adicionar os produtos
    $('#tabelaProdutosPDVVendas tbody').empty();

    // Iterar sobre os produtos adicionados e adicionar na tabela
    produtosAdicionados.forEach(function(produto) {
        $('#tabelaProdutosPDVVendas tbody').append(`
            <tr>
                <td>${produto}</td>
                <td>Valor Unitário</td>
                <td>Quantidade</td>
                <td>Valor Total</td>
                <td>Ações</td>
            </tr>
        `);
    });
}
