// controleDesconto.js

// Função para atualizar o estado do campo de desconto
function atualizarCampoDesconto() {
    var descontoEmReais = $('#descontoEmReais').val() === "true"; // Verifica se a opção de desconto em R$ está selecionada
    var descontoPercentual = $('#descontoPercentual').val() === "true"; // Verifica se a opção de desconto em % está selecionada
    var descontoInput = $('#desconto'); // Seleciona o campo de entrada do desconto
    
    // Verifica se a opção de desconto em R$ ou % está selecionada e habilita/desabilita o campo de entrada do desconto conforme necessário
    if (descontoEmReais || descontoPercentual) {
        descontoInput.prop('disabled', false); // Habilita o campo de entrada do desconto
    } else {
        descontoInput.prop('disabled', true); // Desabilita o campo de entrada do desconto
    }
}

// Adiciona manipuladores de eventos de clique aos botões de desconto
$('#btnDescontoReais').click(function() {
    var isSelected = $('#descontoEmReais').val() === "true";
    $('#descontoEmReais').val(isSelected ? "false" : "true");
    $('#descontoPercentual').val("false");
    atualizarCampoDesconto(); // Atualiza o estado do campo de desconto
});

$('#btnDescontoPercentual').click(function() {
    var isSelected = $('#descontoPercentual').val() === "true";
    $('#descontoPercentual').val(isSelected ? "false" : "true");
    $('#descontoEmReais').val("false");
    atualizarCampoDesconto(); // Atualiza o estado do campo de desconto
});

// Função inicial para atualizar o campo de desconto ao carregar a página
$(document).ready(function() {
    atualizarCampoDesconto();
});
