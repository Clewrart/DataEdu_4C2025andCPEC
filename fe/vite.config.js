import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'

import path from 'path'

export default defineConfig({
    plugins: [
        vue(),
        AutoImport({
            resolvers: [ElementPlusResolver()],
        }),
        Components({
            resolvers: [ElementPlusResolver()],
        }),
    ],
    resolve: {
        alias: {
            "@": path.resolve(__dirname, "src"),
            "@compos": path.resolve(__dirname, "src/components"),
            "@assets": path.resolve(__dirname, "src/assets"),
            "@api": path.resolve(__dirname, "src/api"),
            "@utils": path.resolve(__dirname, "src/utils"),
            "@router": path.resolve(__dirname, "src/router"),
            "@store": path.resolve(__dirname, "src/store"),
        }
    },
    css: {
        preprocessorOptions: {
            scss: {
                additionalData: `
          @import "@/styles/variables.scss";
          @import "@/styles/mixins.scss";
          @import "@/styles/global.scss";
          @import "@/styles/student.scss";
        `
            }
        }
    },
    server: {
        port: 6173,
        host: "0.0.0.0",
        proxy: {
            '/api': {
               target: 'http://124.71.62.19:1152/api',
                //target: "http://127.0.0.1:1152/api",
                changeOrigin: true,
                rewrite: (path) => path.replace(/^\/api/, '')
            },
        }
    }
})
