// Importa las funciones necesarias de vue-router
import { createRouter, createWebHistory } from "vue-router";
// Importa las vistas y componentes que se usarán en las rutas
import LoginView from "../views/LoginView.vue";
import DashPrincipal from "../views/DashPrincipal.vue";
import RegisterUser from "../views/RegisterUser.vue"; // <-- Agregado
// Importa el store de autenticación
import { useAuthStore } from "../store/auth";

// Define las rutas de la aplicación
const routes = [
  { path: "/", redirect: "/login" }, // Redirige la raíz al login
  { path: "/login", component: LoginView }, // Ruta para la vista de login
  { path: "/register", component: RegisterUser }, // <-- Ruta para registro
  {
    path: "/dashboard",
    component: DashPrincipal,
    meta: { requiresAuth: true }, // Esta ruta requiere autenticación
  },
];

// Crea el router con historial HTML5 y las rutas definidas
const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Guarda de navegación para proteger rutas que requieren autenticación
router.beforeEach((to, from, next) => {
  const auth = useAuthStore(); // Obtiene el estado de autenticación
  // Si la ruta requiere autenticación y no hay token, redirige al login
  if (to.meta.requiresAuth && !auth.token) {
    next("/login");
  } else {
    next(); // Si no, permite el acceso
  }
});

export default router; // Exporta el router para usarlo en la app
