<!--
  ProtectedArea.vue

  Este componente representa un área protegida de la aplicación, accesible solo para usuarios autenticados.
  Muestra un mensaje de bienvenida y un botón para cerrar sesión.

  Funcionalidad:
  - Muestra un mensaje indicando que la sesión se ha iniciado con éxito.
  - Permite al usuario cerrar sesión haciendo clic en el botón "Cerrar sesión".
  - Al cerrar sesión, se llama al método `logout` del store de autenticación y se redirige al usuario a la página de login.

  Dependencias:
  - useAuthStore: Store de autenticación para gestionar el estado de login/logout.
  - useRouter: Composable de Vue Router para la navegación programática.

  Métodos:
  - logout: Cierra la sesión del usuario y redirige a la ruta '/login'.
-->
<template>
  <div>
    <h1>Sesión iniciada con éxito</h1>
    <p>¡Estás autenticado!</p>
    <button @click="logout">Cerrar sesión</button>
  </div>
</template>

<script setup>
import { useAuthStore } from '../store/auth'
import { useRouter } from 'vue-router'

const auth = useAuthStore()
const router = useRouter()

const logout = () => {
  auth.logout()
  router.push('/login')
}
</script>
