// vite.config.js
import { defineConfig } from "file:///D:/N01_CSTRecourse/m06_%E5%AD%A6%E6%9C%AF%20%E9%A1%B9%E7%9B%AE/202504%E5%9B%BE%E5%83%8F%E5%B9%B3%E5%8F%B0/01%E5%B9%B3%E5%8F%B0%E4%BB%A3%E7%A0%81/datasci/fe/node_modules/vite/dist/node/index.js";
import vue from "file:///D:/N01_CSTRecourse/m06_%E5%AD%A6%E6%9C%AF%20%E9%A1%B9%E7%9B%AE/202504%E5%9B%BE%E5%83%8F%E5%B9%B3%E5%8F%B0/01%E5%B9%B3%E5%8F%B0%E4%BB%A3%E7%A0%81/datasci/fe/node_modules/@vitejs/plugin-vue/dist/index.mjs";
import AutoImport from "file:///D:/N01_CSTRecourse/m06_%E5%AD%A6%E6%9C%AF%20%E9%A1%B9%E7%9B%AE/202504%E5%9B%BE%E5%83%8F%E5%B9%B3%E5%8F%B0/01%E5%B9%B3%E5%8F%B0%E4%BB%A3%E7%A0%81/datasci/fe/node_modules/unplugin-auto-import/dist/vite.js";
import Components from "file:///D:/N01_CSTRecourse/m06_%E5%AD%A6%E6%9C%AF%20%E9%A1%B9%E7%9B%AE/202504%E5%9B%BE%E5%83%8F%E5%B9%B3%E5%8F%B0/01%E5%B9%B3%E5%8F%B0%E4%BB%A3%E7%A0%81/datasci/fe/node_modules/unplugin-vue-components/dist/vite.js";
import { ElementPlusResolver } from "file:///D:/N01_CSTRecourse/m06_%E5%AD%A6%E6%9C%AF%20%E9%A1%B9%E7%9B%AE/202504%E5%9B%BE%E5%83%8F%E5%B9%B3%E5%8F%B0/01%E5%B9%B3%E5%8F%B0%E4%BB%A3%E7%A0%81/datasci/fe/node_modules/unplugin-vue-components/dist/resolvers.js";
import path from "path";
var __vite_injected_original_dirname = "D:\\N01_CSTRecourse\\m06_\u5B66\u672F \u9879\u76EE\\202504\u56FE\u50CF\u5E73\u53F0\\01\u5E73\u53F0\u4EE3\u7801\\datasci\\fe";
var vite_config_default = defineConfig({
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()]
    }),
    Components({
      resolvers: [ElementPlusResolver()]
    })
  ],
  resolve: {
    // 别名配置
    alias: {
      // 将根目录的src映射为@
      "@": path.resolve(__vite_injected_original_dirname, "src"),
      "@compos": path.resolve(__vite_injected_original_dirname, "src/components"),
      "@assets": path.resolve(__vite_injected_original_dirname, "src/assets"),
      "@api": path.resolve(__vite_injected_original_dirname, "src/api"),
      "@utils": path.resolve(__vite_injected_original_dirname, "src/utils"),
      "@router": path.resolve(__vite_injected_original_dirname, "src/router"),
      "@store": path.resolve(__vite_injected_original_dirname, "src/store")
    }
  },
  define: {
    // __DOCUMENT_BASE_URL: JSON.stringify('http://localhost:5000/download'),
  },
  // 配置代理
  server: {
    port: 6173,
    host: "0.0.0.0",
    proxy: {
      "/api": {
        target: "http://124.71.62.19:1152/api",
        changeOrigin: true,
        rewrite: (path2) => path2.replace(/^\/api/, "")
      }
    }
  }
});
export {
  vite_config_default as default
};
//# sourceMappingURL=data:application/json;base64,ewogICJ2ZXJzaW9uIjogMywKICAic291cmNlcyI6IFsidml0ZS5jb25maWcuanMiXSwKICAic291cmNlc0NvbnRlbnQiOiBbImNvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9kaXJuYW1lID0gXCJEOlxcXFxOMDFfQ1NUUmVjb3Vyc2VcXFxcbTA2X1x1NUI2Nlx1NjcyRiBcdTk4NzlcdTc2RUVcXFxcMjAyNTA0XHU1NkZFXHU1MENGXHU1RTczXHU1M0YwXFxcXDAxXHU1RTczXHU1M0YwXHU0RUUzXHU3ODAxXFxcXGRhdGFzY2lcXFxcZmVcIjtjb25zdCBfX3ZpdGVfaW5qZWN0ZWRfb3JpZ2luYWxfZmlsZW5hbWUgPSBcIkQ6XFxcXE4wMV9DU1RSZWNvdXJzZVxcXFxtMDZfXHU1QjY2XHU2NzJGIFx1OTg3OVx1NzZFRVxcXFwyMDI1MDRcdTU2RkVcdTUwQ0ZcdTVFNzNcdTUzRjBcXFxcMDFcdTVFNzNcdTUzRjBcdTRFRTNcdTc4MDFcXFxcZGF0YXNjaVxcXFxmZVxcXFx2aXRlLmNvbmZpZy5qc1wiO2NvbnN0IF9fdml0ZV9pbmplY3RlZF9vcmlnaW5hbF9pbXBvcnRfbWV0YV91cmwgPSBcImZpbGU6Ly8vRDovTjAxX0NTVFJlY291cnNlL20wNl8lRTUlQUQlQTYlRTYlOUMlQUYlMjAlRTklQTElQjklRTclOUIlQUUvMjAyNTA0JUU1JTlCJUJFJUU1JTgzJThGJUU1JUI5JUIzJUU1JThGJUIwLzAxJUU1JUI5JUIzJUU1JThGJUIwJUU0JUJCJUEzJUU3JUEwJTgxL2RhdGFzY2kvZmUvdml0ZS5jb25maWcuanNcIjtpbXBvcnQgeyBkZWZpbmVDb25maWcgfSBmcm9tICd2aXRlJ1xyXG5pbXBvcnQgdnVlIGZyb20gJ0B2aXRlanMvcGx1Z2luLXZ1ZSdcclxuaW1wb3J0IEF1dG9JbXBvcnQgZnJvbSAndW5wbHVnaW4tYXV0by1pbXBvcnQvdml0ZSdcclxuaW1wb3J0IENvbXBvbmVudHMgZnJvbSAndW5wbHVnaW4tdnVlLWNvbXBvbmVudHMvdml0ZSdcclxuaW1wb3J0IHsgRWxlbWVudFBsdXNSZXNvbHZlciB9IGZyb20gJ3VucGx1Z2luLXZ1ZS1jb21wb25lbnRzL3Jlc29sdmVycydcclxuXHJcbmltcG9ydCBwYXRoIGZyb20gJ3BhdGgnXHJcblxyXG4vLyBodHRwczovL3ZpdGVqcy5kZXYvY29uZmlnL1xyXG5leHBvcnQgZGVmYXVsdCBkZWZpbmVDb25maWcoe1xyXG4gICAgcGx1Z2luczogW1xyXG4gICAgICAgIHZ1ZSgpLFxyXG4gICAgICAgIEF1dG9JbXBvcnQoe1xyXG4gICAgICAgICAgICByZXNvbHZlcnM6IFtFbGVtZW50UGx1c1Jlc29sdmVyKCldLFxyXG4gICAgICAgIH0pLFxyXG4gICAgICAgIENvbXBvbmVudHMoe1xyXG4gICAgICAgICAgICByZXNvbHZlcnM6IFtFbGVtZW50UGx1c1Jlc29sdmVyKCldLFxyXG4gICAgICAgIH0pLFxyXG4gICAgXSxcclxuICAgIHJlc29sdmU6IHtcclxuICAgICAgICAvLyBcdTUyMkJcdTU0MERcdTkxNERcdTdGNkVcclxuICAgICAgICBhbGlhczoge1xyXG4gICAgICAgICAgICAvLyBcdTVDMDZcdTY4MzlcdTc2RUVcdTVGNTVcdTc2ODRzcmNcdTY2MjBcdTVDMDRcdTRFM0FAXHJcbiAgICAgICAgICAgIFwiQFwiOiBwYXRoLnJlc29sdmUoX19kaXJuYW1lLCBcInNyY1wiKSxcclxuICAgICAgICAgICAgXCJAY29tcG9zXCI6IHBhdGgucmVzb2x2ZShfX2Rpcm5hbWUsIFwic3JjL2NvbXBvbmVudHNcIiksXHJcbiAgICAgICAgICAgIFwiQGFzc2V0c1wiOiBwYXRoLnJlc29sdmUoX19kaXJuYW1lLCBcInNyYy9hc3NldHNcIiksXHJcbiAgICAgICAgICAgIFwiQGFwaVwiOiBwYXRoLnJlc29sdmUoX19kaXJuYW1lLCBcInNyYy9hcGlcIiksXHJcbiAgICAgICAgICAgIFwiQHV0aWxzXCI6IHBhdGgucmVzb2x2ZShfX2Rpcm5hbWUsIFwic3JjL3V0aWxzXCIpLFxyXG4gICAgICAgICAgICBcIkByb3V0ZXJcIjogcGF0aC5yZXNvbHZlKF9fZGlybmFtZSwgXCJzcmMvcm91dGVyXCIpLFxyXG4gICAgICAgICAgICBcIkBzdG9yZVwiOiBwYXRoLnJlc29sdmUoX19kaXJuYW1lLCBcInNyYy9zdG9yZVwiKSxcclxuICAgICAgICB9XHJcbiAgICB9LFxyXG4gICAgZGVmaW5lOiB7XHJcbiAgICAgICAgLy8gX19ET0NVTUVOVF9CQVNFX1VSTDogSlNPTi5zdHJpbmdpZnkoJ2h0dHA6Ly9sb2NhbGhvc3Q6NTAwMC9kb3dubG9hZCcpLFxyXG4gICAgfSxcclxuXHJcbiAgICAvLyBcdTkxNERcdTdGNkVcdTRFRTNcdTc0MDZcclxuICAgIHNlcnZlcjoge1xyXG4gICAgICAgIHBvcnQ6IDYxNzMsXHJcbiAgICAgICAgaG9zdDogXCIwLjAuMC4wXCIsXHJcbiAgICAgICAgcHJveHk6IHtcclxuICAgICAgICAgICAgJy9hcGknOiB7XHJcbiAgICAgICAgICAgICAgICB0YXJnZXQ6ICdodHRwOi8vMTI0LjcxLjYyLjE5OjExNTIvYXBpJyxcclxuICAgICAgICAgICAgICAgIFxyXG4gICAgICAgICAgICAgICAgY2hhbmdlT3JpZ2luOiB0cnVlLFxyXG4gICAgICAgICAgICAgICAgcmV3cml0ZTogKHBhdGgpID0+IHBhdGgucmVwbGFjZSgvXlxcL2FwaS8sICcnKVxyXG4gICAgICAgICAgICB9LFxyXG4gICAgICAgIH1cclxuICAgIH1cclxufSkiXSwKICAibWFwcGluZ3MiOiAiO0FBQTZjLFNBQVMsb0JBQW9CO0FBQzFlLE9BQU8sU0FBUztBQUNoQixPQUFPLGdCQUFnQjtBQUN2QixPQUFPLGdCQUFnQjtBQUN2QixTQUFTLDJCQUEyQjtBQUVwQyxPQUFPLFVBQVU7QUFOakIsSUFBTSxtQ0FBbUM7QUFTekMsSUFBTyxzQkFBUSxhQUFhO0FBQUEsRUFDeEIsU0FBUztBQUFBLElBQ0wsSUFBSTtBQUFBLElBQ0osV0FBVztBQUFBLE1BQ1AsV0FBVyxDQUFDLG9CQUFvQixDQUFDO0FBQUEsSUFDckMsQ0FBQztBQUFBLElBQ0QsV0FBVztBQUFBLE1BQ1AsV0FBVyxDQUFDLG9CQUFvQixDQUFDO0FBQUEsSUFDckMsQ0FBQztBQUFBLEVBQ0w7QUFBQSxFQUNBLFNBQVM7QUFBQTtBQUFBLElBRUwsT0FBTztBQUFBO0FBQUEsTUFFSCxLQUFLLEtBQUssUUFBUSxrQ0FBVyxLQUFLO0FBQUEsTUFDbEMsV0FBVyxLQUFLLFFBQVEsa0NBQVcsZ0JBQWdCO0FBQUEsTUFDbkQsV0FBVyxLQUFLLFFBQVEsa0NBQVcsWUFBWTtBQUFBLE1BQy9DLFFBQVEsS0FBSyxRQUFRLGtDQUFXLFNBQVM7QUFBQSxNQUN6QyxVQUFVLEtBQUssUUFBUSxrQ0FBVyxXQUFXO0FBQUEsTUFDN0MsV0FBVyxLQUFLLFFBQVEsa0NBQVcsWUFBWTtBQUFBLE1BQy9DLFVBQVUsS0FBSyxRQUFRLGtDQUFXLFdBQVc7QUFBQSxJQUNqRDtBQUFBLEVBQ0o7QUFBQSxFQUNBLFFBQVE7QUFBQTtBQUFBLEVBRVI7QUFBQTtBQUFBLEVBR0EsUUFBUTtBQUFBLElBQ0osTUFBTTtBQUFBLElBQ04sTUFBTTtBQUFBLElBQ04sT0FBTztBQUFBLE1BQ0gsUUFBUTtBQUFBLFFBQ0osUUFBUTtBQUFBLFFBRVIsY0FBYztBQUFBLFFBQ2QsU0FBUyxDQUFDQSxVQUFTQSxNQUFLLFFBQVEsVUFBVSxFQUFFO0FBQUEsTUFDaEQ7QUFBQSxJQUNKO0FBQUEsRUFDSjtBQUNKLENBQUM7IiwKICAibmFtZXMiOiBbInBhdGgiXQp9Cg==
