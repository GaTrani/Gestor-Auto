// produtos.js

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

    // Atualizar o valor do campo total
    var totalNota = calcularTotalProdutos();
    $('#valorTotalNota').val(totalNota);

    // Verificar se o desconto está vazio
    var desconto = parseFloat($('#desconto').val()) || 0;
    if (desconto === 0) {
        $('#valorTotalComDesconto').val($('#valorTotalNota').val());
    } else {
        var totalComDesconto = totalNota - desconto;
        $('#valorTotalComDesconto').val(totalComDesconto.toFixed(2));
    }
}

function calcularTotalProdutos() {
    let total = 0.0;
    produtosAdicionados.forEach(function (produto) {
        total += produto.precoVenda * produto.quantidade;
    });
    return total.toFixed(2); // Retorna o total com duas casas decimais
}

// Chamada inicial para carregar os produtos do banco de dados
carregarProdutosDoBanco();

$(document).ready(function () {

    atualizarCampoDesconto();


    var pdvId = $("#pdvId").val();

    if (pdvId) {
        fetchPdv(pdvId);
        fetchVendas(pdvId);
    }

    function fetchPdv(pdvId) {
        $.ajax({
            url: 'pdv//detalhes/' + pdvId,
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
                        produto: venda.produto,
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

    // Função para coletar os dados do formulário
    function coletarDadosFormulario() {
        var dados = {
            pdvId: $("#pdvId").val(),
            clienteId: $("#clienteId").val(),
            veiculoId: $("#veiculo").val(),
            km: $("#km").val(),
            desconto: $("#desconto").val(),
            valorTotalNota: $("#valorTotalNota").val(),
            valorTotalComDesconto: $("#valorTotalComDesconto").val(),
            produtos: produtosAdicionados
        };
        return dados;
    }

    // Função para exibir os dados coletados
    function exibirDadosColetados() {
        var dados = coletarDadosFormulario();
        console.log("Dados coletados:", dados);

        // Exibir os dados coletados em um modal (opcional)
        var dadosTexto = JSON.stringify(dados, null, 4); // Formatar JSON para exibição
        alert("Dados coletados:\n" + dadosTexto);

        return dados;
    }

    // Adicionar evento de clique ao botão de salvar
    $("#btnAtualizar").click(function (event) {
        event.preventDefault(); // Impedir o envio padrão do formulário

        var dados = exibirDadosColetados();

        // Perguntar ao usuário se deseja continuar com a atualização
        if (confirm("Deseja continuar com a atualização?")) {
            // Enviar os dados para a rota de atualização
            $.ajax({
                type: "POST",
                contentType: "application/json",
                url: "/pdv/atualizar",
                data: JSON.stringify(dados),
                dataType: 'json',
                success: function (response) {
                    console.log("PDV atualizado com sucesso!");
                    alert("PDV atualizado com sucesso!");
                },
                error: function (xhr, status, error) {
                    console.error("Erro ao atualizar PDV:", error);
                    alert("Erro ao atualizar PDV: " + error);
                }
            });
        }
    });

})

// Função para carregar produtos do banco de dados
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
    let total = 0.0;
    produtosAdicionados.forEach(function (produto) {
        total += produto.precoVenda * produto.quantidade;
    });
    return total.toFixed(2); // Retorna o total com duas casas decimais
}
