// Parte de login
const loginUrl = 'https://gateway.apibrasil.io/api/v2/login';

const loginData = {
    email: 'gabrieltrani02@gmail.com',
    password: 'sitemecanica'
};

fetch(loginUrl, {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify(loginData)
})
    .then(response => response.json())
    .then(data => {
        if (!data.error) {
            const token = data.authorization.token;
            console.log('Token de autenticação:', token);

            // Após fazer login com sucesso, obter informações do veículo
            obterInfoVeiculo(token);
        } else {
            console.error('Falha na solicitação de login:', data.message);
        }
    })
    .catch(error => console.error('Erro ao enviar solicitação de login:', error));

// Função para obter informações do veículo por placa
async function obterInfoVeiculo(token) {
    const dadosUrl = 'https://gateway.apibrasil.io/api/v2/dados';
    const placa = 'HBM6603';
    const dadosVeiculo = { placa };

    const headers = {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}` // Usando o token de autenticação recebido no login
    };

    try {
        const response = await fetch(dadosUrl, {
            method: 'POST',
            headers,
            body: JSON.stringify(dadosVeiculo)
        });

        const data = await response.json();
        if (!data.error) {
            const marca = data.response.MARCA;
            const modelo = data.response.MODELO;
            console.log('Marca:', marca);
            console.log('Modelo:', modelo);
        } else {
            console.error('Falha na solicitação de dados do veículo:', data.message);
        }
    } catch (error) {
        console.error('Erro ao obter informações do veículo:', error);
    }
}
