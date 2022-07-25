const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true
})
// 跨域配置
module.exports = {
  devServer: {                //记住，别写错了devServer//设置本地默认端口  选填
    allowedHosts: [ // Invalid Host header的解决方案
      '6e58a3d4.cpolar.cn', // 允许访问的域名地址，即cpolar内网穿透的地址
      '.6e58a3d4.cpolar.cn'   // .是二级域名的通配符
    ],
    port: 9898, // 设置端口
    proxy: {                 //设置代理，必须填
      '/api': {              //设置拦截器  拦截器格式   斜杠+拦截器名字，名字可以自己定
        target: 'http://localhost:9090',     //代理的目标地址
        changeOrigin: true,              //是否设置同源，输入是的
        pathRewrite: {                   //路径重写
          '^/api': ''                     //选择忽略拦截器里面的内容
        }
      }
    }
  }
}