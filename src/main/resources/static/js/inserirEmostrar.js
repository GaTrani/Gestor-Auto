/* // Lista para armazenar os produtos adicionados
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
    console.log('Lista: ', produtosAdicionados)
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
 */

// Lista para armazenar os produtos adicionados
var produtosAdicionados = [];

// Evento de clique no botão "Adicionar"
$('#btnAdicionar').on('click', function () {
    var produtoSelecionadoId = $('#inputBusca').val();
    console.log('ID do produto selecionado: ', produtoSelecionadoId);

    if (produtoSelecionadoId) {
        // Requisição AJAX para buscar o preço de venda do produto pelo ID
        $.ajax({
            type: 'GET',
            url: '/buscar_valor_unitario',
            data: {
                produtoId: produtoSelecionadoId
            },
            success: function (response) {
                console.log('Resposta do servidor: ', response);
                adicionarProdutoNaLista(produtoSelecionadoId, response); // Adiciona o produto com o preço de venda na lista
            },
            error: function (error) {
                console.error('Erro ao buscar preço de venda do produto: ', error);
                // Adicionar o produto sem preço de venda na lista
                adicionarProdutoNaLista(produtoSelecionadoId, null);
            }
        });
    } else {
        console.error('Erro ao capturar dados para adicionar produto.');
    }
});

// Função para adicionar o produto à lista com o preço de venda
function adicionarProdutoNaLista(produtoId, precoVenda) {
    var novoProduto = {
        id: produtoId,
        precoVenda: precoVenda,
        quantidade: 1, // Quantidade inicial
        valorTotal: precoVenda // Valor total inicial (precoVenda * quantidade)
    };
    console.log('Novo produto adicionado: ', novoProduto);

    // Adicionar o produto à lista de produtos adicionados
    produtosAdicionados.push(novoProduto);

    // Limpar o campo de busca
    $('#inputBusca').val('');

    // Exibir os produtos adicionados na tabela
    atualizarTabelaProdutos();
}

// Função para atualizar a tabela de produtos
function atualizarTabelaProdutos() {
    // Limpar a tabela antes de adicionar os produtos
    $('#tabelaProdutosPDVVendas tbody').empty();
    console.log('Lista de produtos adicionados: ', produtosAdicionados);

    // Iterar sobre os produtos adicionados e adicionar na tabela
    produtosAdicionados.forEach(function (produto) {
        $('#tabelaProdutosPDVVendas tbody').append(`
            <tr>
                <td>${produto.id}</td>
                <td>${produto.precoVenda !== null ? produto.precoVenda : 'Preço não disponível'}</td>
                <td>${produto.quantidade}</td>
                <td>${produto.precoVenda !== null ? (produto.precoVenda * produto.quantidade).toFixed(2) : 'N/A'}</td>
                <td>Ações</td>
            </tr>
        `);
    });
}
