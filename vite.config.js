import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [
    vue(),
  ],
  css: {
    preprocessorOptions: {
      scss: {
        api: "modern-compiler" // or 'modern'
      }
    }
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  server: {
    proxy: {
      '/api': { //获取路径中包含了/api的请求
        target: 'http://localhost:8080', //后台服务所在的源
        changeOrigin: true, //是否跨域
        rewrite: (path)=>path.replace(/^\/api/, '') //重写路径,/api替换''
      }
    }
  }
})
