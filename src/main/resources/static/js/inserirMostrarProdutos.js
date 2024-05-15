$(document).ready(function() {
    // Lista para armazenar os produtos adicionados
    var produtosAdicionados = [];

    // Função para carregar os produtos do banco de dados
    function carregarProdutosDoBanco() {
        $.ajax({
            url: '/pdv/produtos',
            method: 'GET',
            success: function(response) {
                if (response && response.length > 0) {
                    produtos = response;
                    exibirDropdownProdutos();
                } else {
                    console.log('Nenhum produto encontrado.');
                }
            },
            error: function(xhr, status, error) {
                console.error('Erro ao carregar produtos:', error);
            }
        });
    }

    // Função para exibir o dropdown de produtos
    function exibirDropdownProdutos() {
        var listaDropdown = $('#listaProdutos');
        listaDropdown.empty();

        produtos.forEach(function(produto) {
            listaDropdown.append('<li class="dropdown-item">' + produto.produto + '</li>');
        });

        var inputBusca = $('#inputBusca');
        listaDropdown.css({
            'position': 'absolute',
            'top': inputBusca.position().top + inputBusca.outerHeight(),
            'left': inputBusca.position().left,
            'width': inputBusca.outerWidth(),
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

        listaDropdown.show();
    }

    // Filtrar os produtos conforme o texto digitado
    $('#inputBusca').on('input', function() {
        var filtro = $(this).val().toLowerCase();
        var listaDropdown = $('#listaProdutos');
        listaDropdown.empty();

        var produtosFiltrados = produtos.filter(function(produto) {
            return produto.produto.toLowerCase().includes(filtro);
        });

        if (produtosFiltrados.length === 0 && filtro !== "") {
            listaDropdown.append('<li class="dropdown-item disabled">Produto não encontrado</li>');
        } else {
            produtosFiltrados.forEach(function(produto) {
                listaDropdown.append('<li class="dropdown-item" data-produto-id="' + produto.id + '">' + produto.produto + '</li>');
            });
        }

        listaDropdown.show();
    });

    // Adicionar produto à tabela ao clicar no dropdown
    $('#listaProdutos').on('click', '.dropdown-item', function() {
        var produtoId = $(this).data('produto-id');
        var produtoSelecionado = produtos.find(function(produto) {
            return produto.id === produtoId;
        });

        if (produtoSelecionado) {
            adicionarProduto(produtoSelecionado);
        }

        $('#inputBusca').val('');
        $('#listaProdutos').hide();
    });

    // Função para adicionar um produto à tabela
    function adicionarProduto(produto) {
        var quantidade = 1;
        var valorUnitario = produto.valor;
        var valorTotal = valorUnitario * quantidade;

        produtosAdicionados.push({
            produtoId: produto.id,
            produtoNome: produto.produto,
            valorUnitario: valorUnitario,
            quantidade: quantidade,
            valorTotal: valorTotal
        });

        exibirProdutosNaTabela();
    }

    // Função para exibir os produtos na tabela
    function exibirProdutosNaTabela() {
        var tabelaBody = $('#tabelaProdutosPDVVendas tbody');
        tabelaBody.empty();

        produtosAdicionados.forEach(function(item) {
            tabelaBody.append(
                '<tr>' +
                '<td>' + item.produtoNome + '</td>' +
                '<td>R$ ' + item.valorUnitario.toFixed(2) + '</td>' +
                '<td>' + item.quantidade + '</td>' +
                '<td>R$ ' + item.valorTotal.toFixed(2) + '</td>' +
                '<td><button type="button" class="btn btn-danger btn-sm btnRemover" data-produto-id="' + item.produtoId + '">Remover</button></td>' +
                '</tr>'
            );
        });
    }

    // Remover produto da tabela ao clicar no botão Remover
    $('#tabelaProdutosPDVVendas').on('click', '.btnRemover', function() {
        var produtoId = $(this).data('produto-id');
        produtosAdicionados = produtosAdicionados.filter(function(item) {
            return item.produtoId !== produtoId;
        });

        exibirProdutosNaTabela();
    });

    // Carregar produtos do banco ao iniciar a página
    carregarProdutosDoBanco();
});
