
module.exports = {
    devServer: {
        proxy: {
            '/api': {
                target: 'http://localhost:8090/api',
                changeOrigin: true,
                pathRewrite: {
                    '^/api': ''
                }
            }
        },
        disableHostCheck: true
    },
    css: {
        loaderOptions: {
            less: {
                lessOptions: {
                    javascriptEnabled: true
                }
            }
        }
    },
    transpileDependencies: [
        'vue2-org-tree'
    ],
}