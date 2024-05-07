// Função para filtrar os itens do dropdown com base no texto digitado
function filtrarProdutos() {
    console.log('chamando a funcao.')
    var filtro = $('#inputBusca').val().toLowerCase(); // Obter o texto digitado e converter para minúsculas
    $('#produto option').each(function() { // Iterar sobre cada opção do dropdown
        var nomeProduto = $(this).text().toLowerCase(); // Obter o nome do produto da opção e converter para minúsculas
        console.log('item: ', nomeProduto)
        if (nomeProduto.indexOf(filtro) > -1 || filtro === "") { // Verificar se o nome do produto contém o texto digitado ou se o filtro está vazio
            $(this).show(); // Mostrar a opção se corresponder ao filtro ou se o filtro estiver vazio
        } else {
            $(this).hide(); // Ocultar a opção se não corresponder ao filtro
        }
    });
}

// Adicionar um evento de digitação ao campo de entrada
$('#inputBusca').on('input', function() {
    filtrarProdutos(); // Chamar a função de filtragem ao digitar algo
});

// Adicionar um evento de foco ao dropdown para mostrar todas as opções ao clicar
$('#produto').on('click', function() {
    $(this).find('option').show(); // Mostrar todas as opções do dropdown
});

// Adicionar um evento de saída do dropdown para filtrar novamente ao sair
$('#produto').on('blur', function() {
    console.log('chamando a funcao.2')
    filtrarProdutos(); // Chamar a função de filtragem ao sair do dropdown
});


/*

function filtrarProdutosPDV() {
    $('#produto').on('input', function () {
        var input = $(this).val();

        // Verifica se o campo de entrada tem pelo menos 3 caracteres
        if (input.length >= 3) {
            // Envia uma solicitação AJAX para o servidor para obter os produtos correspondentes
            $.ajax({
                url: '/produtos/filtrar',
                type: 'GET',
                data: { input: input },
                success: function (response) {
                    // Limpa o contêiner de resultados
                    $('#resultado-produtos').empty();

                    // Adiciona os produtos correspondentes ao contêiner de resultados
                    $.each(response.produtos, function (index, produto) {
                        $('#resultado-produtos').append('<div>' + produto.nome + '</div>');
                    });
                }
            });
        } else {
            // Limpa o contêiner de resultados se o campo de entrada estiver vazio
            $('#resultado-produtos').empty();
        }
    });
}

// Chama a função filtrarProdutosPDV quando o documento estiver pronto
$(document).ready(function () {
    filtrarProdutosPDV();
});


function dropListaProdutos() {
    // JavaScript para fazer a solicitação AJAX e preencher o dropdown com os produtos
    $(document).ready(function () {
        // Fazer solicitação AJAX para obter a lista de produtos
        $.get("/api/produtos", function (produtos) {
            // Preencher o dropdown com os produtos retornados
            var dropdown = $("#produtoDropdown");
            dropdown.empty();
            $.each(produtos, function (index, produto) {
                dropdown.append($("<option></option>").attr("value", produto.id).text(produto.nome));
            });
        });
    });

}

$(document).ready(function() {
    // Função para carregar os produtos no dropdown
    function carregarProdutos() {
        $.ajax({
            type: "GET",
            url: "/produtos", // Endpoint para listar os produtos
            success: function(produtos) {
                // Limpa as opções atuais
                $('#produto').empty();
                // Adiciona uma opção vazia para permitir a seleção de nenhum produto
                $('#produto').append('<option value="">Selecione um produto</option>');
                // Preenche o dropdown com os produtos obtidos
                $.each(produtos, function(index, produto) {
                    $('#produto').append('<option value="' + produto.id + '">' + produto.nome + '</option>');
                });
            },
            error: function() {
                console.error('Erro ao carregar os produtos.');
            }
        });
    }

    // Chama a função para carregar os produtos quando a página é carregada
    carregarProdutos();
});
 */