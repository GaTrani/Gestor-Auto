// tools.js

const { promisify } = require('util');
const request = require('request');

const sleep = (ms) => new Promise((res) => setTimeout(res, ms));

const retry = async (options, attempt = 0, delay = 0, maximumRetry = 0) => {
    try {
        await sleep(delay);
        const { statusCode, body } = await promisify(request)(options);
        return { statusCode, body };
    } catch (e) {
        if (attempt >= maximumRetry) throw Error(e);
        return retry(options, attempt + 1, (delay || 1000) * 2);
    }
};

module.exports = {
    retry,
};
