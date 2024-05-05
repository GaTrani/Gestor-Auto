document.getElementById('seuFormulario').addEventListener('submit', function(event) {
    // Impede o envio do formulário antes da formatação da data
    event.preventDefault();

    // Obtém o valor do input de data
    let dataVencimento = document.getElementById('dataVencimento').value;

    // Converte a data para o formato yyyy-MM-dd
    let partesData = dataVencimento.split('/');
    let dataFormatada = partesData[2] + '-' + partesData[1] + '-' + partesData[0];

    // Define o valor do input como a data formatada
    document.getElementById('dataVencimento').value = dataFormatada;

    // Envia o formulário
    this.submit();
});
