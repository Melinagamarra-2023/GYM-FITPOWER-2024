<template>
  <div class="login">
    <h2>Login</h2>
    <form @submit.prevent="handleLogin">
      <!-- Campo para el nombre de usuario -->
      <input v-model="username" placeholder="Usuario" required />
      <!-- Campo para la contraseña -->
      <input v-model="password" type="password" placeholder="Contraseña" required />
      <!-- Botón para enviar el formulario -->
      <button type="submit">Iniciar Sesión</button>
      <!-- Mensaje de error si las credenciales son incorrectas -->
      <p v-if="error" style="color: red">{{ error }}</p>
    </form>
  </div>
</template>

<script setup>
// Importa las funciones necesarias de Vue y otras dependencias
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'

// Variables reactivas para los campos del formulario y el mensaje de error
const username = ref('')
const password = ref('')
const error = ref('')

// Obtiene la instancia del store de autenticación y el router
const auth = useAuthStore()
const router = useRouter()

// Función que maneja el inicio de sesión
const handleLogin = async () => {
  try {
    // Intenta iniciar sesión usando el store de autenticación
    await auth.login(username.value, password.value)
    // Redirige al usuario a la ruta protegida si el login es exitoso
    router.push('/dashboard')
  } catch (err) {
    // Muestra un mensaje de error si las credenciales son incorrectas
    error.value = 'Usuario o contraseña incorrectos'
  }
}
</script>
