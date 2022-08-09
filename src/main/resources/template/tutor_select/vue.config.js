const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  devServer:{
    proxy:{
      '/Student':{
        target:'http://10.16.88.144:8080',
        changeOrigin:true
      }
    }
  }
})
