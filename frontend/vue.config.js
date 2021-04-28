const fs = require('fs');
module.exports = {
    devServer: {
        proxy: "http://localhost:8080/api/v1",
        https: {
            ca: fs.readFileSync('../backend/src/main/resources/keystore/server.pem')
        },
    },
};