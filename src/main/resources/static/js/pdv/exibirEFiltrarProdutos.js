// Lista de produtos inicialmente vazia
var produtos = [];

// Função para exibir o dropdown de produtos
function exibirDropdownProdutos() {
    var listaDropdown = $('#listaProdutos'); // Selecionar o elemento da lista de produtos
    listaDropdown.empty(); // Limpar a lista de produtos

    // Adicionar cada produto como uma opção no dropdown
    produtos.forEach(function (produto) {
        listaDropdown.append('<li class="dropdown-item" data-produto-id="' + produto.id + '">' + produto.produto + '</li>');
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

// Função para carregar os produtos do banco de dados
function carregarProdutosDoBanco() {
    console.log('entrou no carregar produto');
    // Fazer uma solicitação AJAX para obter os produtos do banco de dados
    $.ajax({
        url: '/pdv/produtos', // URL da sua API que retorna os produtos
        method: 'GET',
        success: function (response) {
            if (response && response.length > 0) {
                produtos = response;
                exibirDropdownProdutos();
            } else {
                console.log('Nenhum produto encontrado.');
            }
        },
        error: function (xhr, status, error) {
            console.error('Erro ao carregar produtos:', error);
        }
    });
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
            listaDropdown.append('<li class="dropdown-item" data-produto-id="' + produto.id + '">' + produto.produto + '</li>');
        });
    }

    listaDropdown.show(); // Exibir o dropdown de produtos
});

// Chamada inicial para carregar os produtos do banco de dados
carregarProdutosDoBanco();
