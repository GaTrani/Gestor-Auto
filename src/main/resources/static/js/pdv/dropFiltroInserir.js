// Lista para armazenar os produtos adicionados
var produtosAdicionados = [];

// Lista de produtos inicialmente vazia
var produtos = [];

// Função para adicionar o produto à lista com o preço de venda
function adicionarProdutoNaLista(produtoId, precoVenda, nomeProduto) {
    var novoProduto = {
        id: produtoId,
        produto: nomeProduto, // Nome do produto
        precoVenda: precoVenda,
        quantidade: 1 // Quantidade inicial
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
    produtosAdicionados.forEach(function (produto, index) {
        // Construir a linha da tabela com um campo de input para a quantidade
        var linhaTabela = `
            <tr>
                <td>${produto.produto}</td>
                <td>${produto.precoVenda !== null ? produto.precoVenda : 'Preço não disponível'}</td>
                <td class="col-qtd">
                    <input type="number" class="quantidade" value="${produto.quantidade}" min="1" step="1">
                </td>
                <td>${produto.precoVenda !== null ? (produto.precoVenda * produto.quantidade).toFixed(2) : 'N/A'}</td>
                <td>
                    <a href="#" class="apagar-produto" data-index="${index}" data-venda-id="${produto.id || ''}">
                        <i class="fas fa-trash-alt d-flex align-items-center justify-content-center" style="color: #E74A3B"></i>
                    </a>
                </td>
            </tr>
        `;

        // Adicionar a linha à tabela
        $('#tabelaProdutosPDVVendas tbody').append(linhaTabela);
    });

    // Adicionar um evento de mudança para os campos de input de quantidade
    $('.quantidade').change(function () {
        // Obter o índice da linha na tabela
        var indiceLinha = $(this).closest('tr').index();

        // Obter a nova quantidade do campo de input
        var novaQuantidade = parseInt($(this).val());

        // Atualizar a quantidade do produto na lista produtosAdicionados
        produtosAdicionados[indiceLinha].quantidade = novaQuantidade;

        // Atualizar a tabela de produtos
        atualizarTabelaProdutos();
    });

    // Adicionar evento de clique para apagar o produto
    $('.apagar-produto').click(function (event) {
        event.preventDefault();
        var indiceProduto = $(this).data('index');
        var vendaId = $(this).data('venda-id');

        // Se o produto tem um vendaId, remove-o do servidor
        if (vendaId) {
            $.ajax({
                url: '/pdv/vendas/' + vendaId,
                method: 'DELETE',
                success: function () {
                    console.log('Venda removida com sucesso!');
                },
                error: function (xhr, status, error) {
                    console.error('Erro ao remover a venda:', error);
                }
            });
        }

        // Remover o produto da lista
        produtosAdicionados.splice(indiceProduto, 1);
        // Atualizar a tabela de produtos
        atualizarTabelaProdutos();
    });

    // Atualizar o valor do campo total
    $('#valorTotalNota').val(calcularTotalProdutos());
}


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

// Função para verificar os produtos adicionados antes de enviar ao controlador
function verificarProdutosAdicionados() {
    console.log('Produtos adicionados: ', produtosAdicionados);
}

// Função para preparar os dados dos produtos antes de enviar o formulário
function prepararDadosProdutos() {
    var produtosJson = JSON.stringify(produtosAdicionados);
    $('#produtosJson').val(produtosJson);
}

function calcularTotalProdutos() {
    let total = 0;
    produtosAdicionados.forEach(function (produto) {
        total += produto.precoVenda * produto.quantidade;
    });
    return total.toFixed(2); // Retorna o total com duas casas decimais
}

// Adiciona a função para preparar os dados ao evento de submit do formulário
$('#form').on('submit', function (event) {
    prepararDadosProdutos();
});

// Chamada inicial para carregar os produtos do banco de dados
carregarProdutosDoBanco();


$(document).ready(function () {
    var pdvId = $("#pdvId").val();

    if (pdvId) {
        fetchPdv(pdvId);
        fetchVendas(pdvId);
    }

    function fetchPdv(pdvId) {
        $.ajax({
            url: '/detalhes/' + pdvId,
            method: 'GET',
            success: function (pdv) {
                $("#desconto").val(pdv.desconto);
                $("#valorTotalNota").val(pdv.valorTotalNota);
                $("#valorTotalComDesconto").val(pdv.valorTotalComDesconto);
                // Adicione mais campos se necessário
            },
            error: function (xhr, status, error) {
                console.error('Erro ao buscar o PDV:', error);
            }
        });
    }

    function fetchVendas(pdvId) {
        $.ajax({
            url: '/pdv/' + pdvId + '/vendas',
            method: 'GET',
            success: function (vendas) {
                var tbody = $('#tabelaProdutosPDVVendas tbody');
                tbody.empty();
                produtosAdicionados = []; // Limpar a lista de produtos adicionados
                vendas.forEach(function (venda) {
                    var novoProduto = {
                        id: venda.idProduto,
                        produto: venda.nomeProduto,
                        precoVenda: venda.valorUnitario,
                        quantidade: venda.quantidade
                    };
                    produtosAdicionados.push(novoProduto); // Adicionar cada venda à lista de produtos adicionados
                });
                atualizarTabelaProdutos();
            },
            error: function (xhr, status, error) {
                console.error('Erro ao buscar as vendas:', error);
            }
        });
    }

    function removerVenda(vendaId) {
        $.ajax({
            url: '/pdv/vendas/' + vendaId,
            method: 'DELETE',
            success: function () {
                console.log('Venda removida com sucesso!');
                fetchVendas(pdvId);  // Atualizar a lista de vendas após remoção
            },
            error: function (xhr, status, error) {
                console.error('Erro ao remover a venda:', error);
            }
        });
    }

    if (pdvId) {
        fetchVendas(pdvId);
    }

    $("#btnSalvar").click(function () {
        var clienteId = $("#clienteId").val();
        var pdvId = $("#pdvId").val();

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/pdv/criar",
            data: JSON.stringify({ clienteId: clienteId, pdvId: pdvId }),
            dataType: 'json',
            success: function (response) {
                console.log("PDV salvo com sucesso!");
            },
            error: function (xhr, status, error) {
                console.error("Erro ao salvar PDV:", error);
            }
        });
    });
})
