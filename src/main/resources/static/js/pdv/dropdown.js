// dropdown.js

// Lista de produtos inicialmente vazia
var produtos = [];
// Função para exibir o dropdown de produtos
function exibirDropdownProdutos() {
    var listaDropdown = $('#listaProdutos'); // Selecionar o elemento da lista de produtos
    listaDropdown.empty(); // Limpar a lista de produtos

    // Adicionar cada produto como uma opção no dropdown
    produtos.forEach(function (produto) {
        listaDropdown.append('<li class="dropdown-item" data-produto-id="' + produto.id + '" data-produto-nome="' + produto.produto + '">' + produto.produto + '</li>');
    });

    // Posicionar o dropdown abaixo do campo de busca
    var inputBusca = $('#inputBusca');
    var listaPosicaoTop = inputBusca.offset().top + inputBusca.outerHeight();
    var listaPosicaoLeft = inputBusca.offset().left;
    var listaLargura = inputBusca.outerWidth();

    listaDropdown.css({
        'position': 'absolute',
        'top': listaPosicaoTop,
        'left': listaPosicaoLeft,
        'width': listaLargura,
        'z-index': '9999',
        'background-color': '#fff',
        'border': '1px solid #ced4da',
        'border-radius': '0.25rem',
        'padding': '0.5rem',
        'list-style-type': 'none',
        'box-shadow': '0px 8px 16px 0px rgba(0,0,0,0.2)',
        'max-height': '200px',
        'overflow-y': 'auto'
    });

    listaDropdown.show(); // Exibir o dropdown de produtos
}

// Adicionar um evento de clique ao campo de busca para exibir o dropdown de produtos
$('#inputBusca').on('click', function (event) {
    if ($('#listaProdutos').is(':visible')) {
        $('#listaProdutos').hide();
    } else {
        event.stopPropagation(); // Impedir a propagação do evento de clique para os elementos pais
        exibirDropdownProdutos(); // Chamar a função para exibir o dropdown de produtos
    }
});

// Fechar o dropdown de produtos quando clicar fora dele
$(document).on('click', function (event) {
    if (!$(event.target).closest('#inputBusca').length && !$(event.target).closest('#listaProdutos').length) {
        $('#listaProdutos').hide(); // Esconder o dropdown de produtos se clicar fora dele
    }
});

// Filtrar os produtos conforme o texto digitado
$('#inputBusca').on('input', function () {
    var filtro = $(this).val().toLowerCase(); // Obter o texto digitado e converter para minúsculas
    var listaDropdown = $('#listaProdutos'); // Selecionar o elemento do dropdown de produtos

    // Limpar a lista de produtos
    listaDropdown.empty();

    // Filtrar as opções do dropdown de produtos com base no texto digitado
    var produtosFiltrados = produtos.filter(function (produto) {
        return produto.produto.toLowerCase().includes(filtro);
    });

    // Verificar se a lista de produtos filtrados está vazia
    if (produtosFiltrados.length === 0 && filtro !== "") {
        // Adicionar mensagem de "produto não encontrado"
        listaDropdown.append('<li class="dropdown-item disabled">Produto não encontrado</li>');
    } else {
        // Adicionar os produtos filtrados à lista dropdown
        produtosFiltrados.forEach(function (produto) {
            listaDropdown.append('<li class="dropdown-item" data-produto-id="' + produto.id + '" data-produto-nome="' + produto.produto + '">' + produto.produto + '</li>');
        });
    }

    listaDropdown.show(); // Exibir o dropdown de produtos
});

// Selecionar o produto clicado e preencher o campo de busca
$('#listaProdutos').on('click', 'li', function (event) {
    var produtoSelecionadoId = $(this).data('produtoId');
    var produtoSelecionadoNome = $(this).data('produtoNome'); // Obter o nome do produto selecionado
    $('#inputBusca').val(produtoSelecionadoNome); // Preencher o campo de busca com o produto selecionado
    $('#inputBusca').data('produtoId', produtoSelecionadoId); // Armazenar o ID do produto no campo de busca
    $('#listaProdutos').hide(); // Esconder o dropdown de produtos após a seleção

    console.log('Produto selecionado:', produtoSelecionadoNome, 'ID:', produtoSelecionadoId);

    // Requisição AJAX para buscar o preço de venda do produto pelo ID
    $.ajax({
        type: 'GET',
        url: '/pdv/buscar_valor_unitario',
        data: {
            produtoId: produtoSelecionadoId
        },
        success: function (response) {
            console.log('Resposta do servidor: ', response);
            adicionarProdutoNaLista(produtoSelecionadoId, response, produtoSelecionadoNome); // Adiciona o produto com o preço de venda e o nome na lista
        },
        error: function (error) {
            console.error('Erro ao buscar preço de venda do produto: ', error);
            // Adicionar o produto sem preço de venda na lista
            adicionarProdutoNaLista(produtoSelecionadoId, null, produtoSelecionadoNome);
        }
    });

    // Impedir a propagação do evento de clique para os elementos pais
    event.stopPropagation();
});

/* // Função para verificar os produtos adicionados antes de enviar ao controlador
function verificarProdutosAdicionados() {
    console.log('Produtos adicionados: ', produtosAdicionados);
}


// Função para preparar os dados dos produtos antes de enviar o formulário
function prepararDadosProdutos() {
    var produtosJson = JSON.stringify(produtosAdicionados);
    $('#produtosJson').val(produtosJson);
}

function calcularTotalProdutos() {
    let total = 0.0;
    produtosAdicionados.forEach(function (produto) {
        total += produto.precoVenda * produto.quantidade;
    });
    return total.toFixed(2); // Retorna o total com duas casas decimais
}

// Adiciona a função para preparar os dados ao evento de submit do formulário
$('#form').on('submit', function (event) {
    prepararDadosProdutos();
}); */



// Chamada inicial para carregar os produtos do banco de dados
carregarProdutosDoBanco();
