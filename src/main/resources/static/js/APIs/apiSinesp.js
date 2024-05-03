const sinespApi = require('./caminho/para/sinespApi');

(async () => {
    try {
        // Configura a API
        sinespApi.configure({
            host: 'apicarros.com',
            serviceVersion: 'v1',
            endpoint: 'consulta',
            timeout: 5000, // Tempo limite de 5 segundos para a solicitação
            maximumRetry: 3, // Tentativas máximas de 3 em caso de falha
        });

        // Realiza a consulta do veículo
        const vehicle = await sinespApi.search('AAA1111');

        // Exibe os dados do veículo
        console.log('Marca:', vehicle.marca);
        console.log('Modelo:', vehicle.modelo);
        console.log('Ano:', vehicle.ano);
        console.log('Cor:', vehicle.cor);
        console.log('Chassi:', vehicle.chassi);
        // E assim por diante...

    } catch (error) {
        console.error('Erro ao consultar veículo:', error.message);
    }
})();
