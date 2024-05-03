function formatarCPF() {
    let cpf = document.getElementById('cpf');
    let valor = cpf.value;

    valor = valor.replace(/\D/g, '');
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
    valor = valor.replace(/(\d{3})(\d)/, '$1.$2');
    valor = valor.replace(/(\d{3})(\d{1,2})$/, '$1-$2');

    cpf.value = valor;
}

function formatarTelefone() {
    let telefone = document.getElementById('telefone');
    let valor = telefone.value;

    valor = valor.replace(/\D/g, '');
    valor = valor.replace(/^(\d{2})(\d)/g, '($1) $2');
    valor = valor.replace(/(\d{5})(\d)/, '$1-$2');

    telefone.value = valor;
}

function formatarCEP() {
    let cep = document.getElementById('cep');
    let valor = cep.value;

    valor = valor.replace(/\D/g, '');
    valor = valor.replace(/(\d{5})(\d)/, '$1-$2');

    cep.value = valor;
}

document.getElementById('cpf').addEventListener('input', formatarCPF);
document.getElementById('telefone').addEventListener('input', formatarTelefone);
document.getElementById('cep').addEventListener('input', formatarCEP);
