// Lista para armazenar os produtos adicionados
var produtosAdicionados = [];

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
    $('#inputBusca').removeData('produtoId'); // Remove o dado armazenado

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

// Selecionar o produto clicado e preencher o campo de busca
$('#listaProdutos').on('click', 'li', function (event) {
    var produtoSelecionadoId = $(this).data('produtoId');
    var produtoSelecionadoNome = $(this).text(); // Obter o texto do produto selecionado
    $('#inputBusca').val(produtoSelecionadoNome); //
    $('#inputBusca').val(produtoSelecionadoNome); // Preencher o campo de busca com o produto selecionado
    $('#inputBusca').data('produtoId', produtoSelecionadoId); // Armazenar o ID do produto no campo de busca
    $('#listaProdutos').hide(); // Esconder o dropdown de produtos após a seleção

    console.log('Produto selecionado:', produtoSelecionadoNome, 'ID:', produtoSelecionadoId);

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

    // Impedir a propagação do evento de clique para os elementos pais
    event.stopPropagation();
});
