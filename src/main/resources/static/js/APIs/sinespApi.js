// sinespApi.js

const { retry } = require('./tools');

const DEFAULT = {
    host: 'apicarros.com',
    endpoint: 'consulta',
    serviceVersion: 'v1',
    timeout: 0,
    maximumRetry: 0,
    proxy: {},
};

let opts = {};

const validate = async (plate) => {
    // Função para validar o formato da placa
};

const request = async (plate) => {
    // Função para fazer a requisição à API
};

const search = async (plate = '') => {
    try {
        // 1. Valide a placa (opcional)
        const plateToUse = await validate(plate);

        // 2. Envie a solicitação para a API e obtenha a resposta
        const response = await request(plateToUse);

        // 3. Retorne os dados obtidos da API
        return response;
    } catch (error) {
        // 4. Se ocorrer algum erro, lance-o para que seja tratado externamente
        throw error;
    }
};


const configure = ({
    host,
    endpoint,
    serviceVersion,
    timeout,
    maximumRetry,
    proxy = {},
} = {}) => {
    // Função para configurar as opções da API
};

module.exports = {
    configure,
    search,
};
