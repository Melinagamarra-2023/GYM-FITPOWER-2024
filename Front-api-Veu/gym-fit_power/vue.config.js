// Importa la función defineConfig desde @vue/cli-service
const { defineConfig } = require("@vue/cli-service");

// Exporta la configuración personalizada de Vue CLI
module.exports = defineConfig({
  // Indica que las dependencias deben ser transpiladas para compatibilidad
  transpileDependencies: true,
  devServer: {
    // Define el host donde se ejecutará el servidor de desarrollo
    host: "localhost",
    // Define el puerto donde se ejecutará el servidor de desarrollo
    port: 8082, // podés cambiarlo si querés usar otro
    client: {
      // Configura la URL del WebSocket para el cliente (hot reloading, etc.)
      webSocketURL: "ws://localhost:8082/ws",
    },
    // Configuración del proxy para redirigir llamadas a la API
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        secure: false
      }
    }
  },
});

// Este archivo configura el servidor de desarrollo de Vue CLI para que escuche en el puerto 8082
// y establezca una conexión WebSocket en la misma dirección. Esto es útil para el hot reloading y otras características de desarrollo en tiempo real.
