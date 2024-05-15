// Lista de produtos inicialmente vazia
var produtos = [];

// Função para exibir o dropdown de produtos
function exibirDropdownProdutos() {
    var listaDropdown = $('#listaProdutos'); // Selecionar o elemento da lista de produtos
    listaDropdown.empty(); // Limpar a lista de produtos

    // Adicionar cada produto como uma opção no dropdown
    produtos.forEach(function (produto) {
        /* console.log('id: ', produto.id,' item= ', produto.produto) */
        listaDropdown.append('<li class="dropdown-item">' + produto.produto + '</li>');
    });

    // Posicionar o dropdown abaixo do campo de busca
    var inputBusca = $('#inputBusca');
    var listaPosicaoTop = inputBusca;
    var listaPosicaoLeft = inputBusca;
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
    console.log('entrou no carregar produto')
    // Fazer uma solicitação AJAX para obter os produtos do banco de dados
    $.ajax({
        url: '/pdv/produtos', // URL da sua API que retorna os produtos
        method: 'GET',
        success: function (response) {
            // Verificar se a resposta foi bem-sucedida e se há produtos
            /* console.log('produtos', response); */
            if (response && response.length > 0) {
                // Iterar sobre a lista de produtos
                response.forEach(function (produto) {
                    // Aqui você pode fazer um console.log de cada produto
                    /* console.log('entrou no for', produto); */
                });
                // Atualizar a lista de produtos com os produtos do banco de dados
                produtos = response;
                // Exibir os novos produtos na lista dropdown
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
        //return; // Se o dropdown já estiver visível, não fazer nada
    }
    /* console.log('input...') */
    event.stopPropagation(); // Impedir a propagação do evento de clique para os elementos pais
    exibirDropdownProdutos(); // Chamar a função para exibir o dropdown de produtos
});


// Fechar o dropdown de produtos quando clicar fora dele
$(document).on('click', function (event) {
    /* console.log('fechar quando clica..') */
    if (!$(event.target).closest('#inputBusca').length || !$(event.target).closest('#listaProdutos').length) {
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
            listaDropdown.append('<li class="dropdown-item">' + produto.produto + '</li>');
        });
    }

    listaDropdown.show(); // Exibir o dropdown de produtos
});



// Selecionar o produto clicado e preencher o campo de busca
$('#listaProdutos').on('click', 'li', function (event) {
    event.stopPropagation(); // Impedir a propagação do evento de clique para os elementos pais
    var produtoSelecionado = $(this).text(); // Obter o texto do produto selecionado
    $('#inputBusca').val(produtoSelecionado); // Preencher o campo de busca com o produto selecionado
    $('#listaProdutos').hide(); // Esconder o dropdown de produtos após a seleção
    console.log('fechando depois de selecionar.')
});

// Chamada inicial para carregar os produtos do banco de dados
carregarProdutosDoBanco();

/* function obterUltimoIdPDV() {
    $.ajax({
        url: '/pdv/ultimo-id',
        method: 'GET',
        success: function(response) {
            console.log('Último ID do PDV:', response);
            $('#pdvId').val(response); // Preencher o campo com o último ID do PDV
        },
        error: function(xhr, status, error) {
            console.error('Erro ao obter o último ID do PDV:', error);
        }
    });
}
 */

// Chame esta função quando a página pdv/novo for carregada
$(document).ready(function() {
    //carregarProdutosDoBanco();
    obterUltimoIdPDV();
    var idPdv = obterUltimoIdPDV(); // obterIdPDV() Substitua por sua lógica para obter o ID do PDV
    carregarProdutosDoPDV(idPdv);

    //evento para o drop cartao
    $('#btnCartao').click(function() {
        // Alternar a classe "show" na div de opções para pagamento com cartão
        $('#cartaoOpcoes').collapse('toggle');
    });
});


// Evento de clique no botão "Adicionar"
/* $('#btnAdicionar').on('click', function() {
    var produtoSelecionado = $('#inputBusca').val();
    var ultimoIdPDV = obterUltimoIdPDV();
    if (produtoSelecionado && ultimoIdPDV) {
        $.ajax({
            url: '/pdv/adicionar-produto-parametros',
            method: 'POST',
            data: {
                id_pdv: ultimoIdPDV,
                produto: produtoSelecionado
            },
            success: function(response) {
                console.log('Produto adicionado com sucesso:', response);
            },
            error: function(xhr, status, error) {
                console.error('Erro ao adicionar produto:', error);
            }
        });
    } else {
        console.error('Erro ao capturar dados para adicionar produto.');
    }
}); */


// Função para obter o último ID do PDV
function obterUltimoIdPDV() {
    var ultimoIdPDV = null;
    $.ajax({
        url: '/pdv/ultimo-id',
        method: 'GET',
        async: false, // Definir como síncrono para esperar a resposta antes de prosseguir
        success: function(response) {
            // Último ID do PDV capturado com sucesso
            ultimoIdPDV = response;
            console.log('Último ID do PDV:', ultimoIdPDV);
            $('#pdvId').val(ultimoIdPDV); // Preencher o campo com o último ID do PDV
        },
        error: function(xhr, status, error) {
            // Erro ao obter o último ID do PDV
            console.error('Erro ao obter o último ID do PDV:', error);
        }
    });
    return ultimoIdPDV;
}

// Função para carregar os produtos deste PDV
function carregarProdutosDoPDV(idPdv) {
    console.log('id===', idPdv)
    $.ajax({
        url: '/pdv/' + idPdv + '/produtos',
        method: 'GET',
        success: function(response) {
            console.log('response: ', response)
            // Limpar a tabela antes de adicionar os novos produtos
            $('#tabelaProdutosPDVVendas tbody').empty();

            // Iterar sobre os produtos e adicionar na tabela
            response.forEach(function(produto) {
                $('#tabelaProdutosPDVVendas tbody').append(`
                    <tr>
                        <td>${produto.produto}</td>
                        <td>${produto.valorUnitario}</td>
                        <td>${produto.quantidade}</td>
                        <td>${produto.total}</td>
                        <td>Ações</td>
                    </tr>
                `);
            });
        },
        error: function(xhr, status, error) {
            console.error('Erro ao carregar tabela produtos do PDV:', error);
        }
    });
}

$(document).ready(function() {
    $("#btnSalvar").click(function() {
        var clienteId = $("#clienteId").val();
        var pdvId = $("#pdvId").val();

        // Enviar os dados para o backend
        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/pdv/criar",
            data: JSON.stringify({ clienteId: clienteId, pdvId: pdvId }),
            dataType: 'json',
            success: function(response) {
                // Manipular a resposta, se necessário
                console.log("PDV salvo com sucesso!");
            },
            error: function(xhr, status, error) {
                // Manipular erros, se necessário
                console.error("Erro ao salvar PDV:", error);
            }
        });
    });
});



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
