// Importa la función para crear una aplicación Vue
import { createApp } from "vue";
// Importa la función para crear el store Pinia
import { createPinia } from "pinia";
// Importa el componente principal de la aplicación
import App from "./App.vue";
// Importa el enrutador de la aplicación
import router from "./router";

// Crea una nueva instancia de la aplicación Vue usando el componente principal
const app = createApp(App);

// Agrega Pinia (gestor de estado) a la aplicación
app.use(createPinia());

// Agrega el enrutador a la aplicación
app.use(router);

// Monta la aplicación en el elemento con id "app" en el HTML
app.mount("#app");
