<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6 col-lg-4">
        <div class="card shadow-sm">
          <div class="card-body p-4">
            <div class="text-center mb-4">
              <img 
                src="@/assets/logo.png" 
                alt="Logo" 
                class="mb-3" 
                style="height: 60px;"
              >
                <h2 class="h4">Acceso al Sistema</h2>
              <p class="text-muted">Ingrese sus credenciales</p>
            </div>

            <form @submit.prevent="handleLogin">
              <div class="mb-3">
                <label for="username" class="form-label">Usuario</label>
                <input
                  id="username"
                  v-model="username"
                  type="text"
                  class="form-control"
                  :class="{ 'is-invalid': error }"
                  placeholder="Correo electronico"
                  required
                  autofocus
                >
              </div>

              <div class="mb-3">
                <label for="password" class="form-label">Contraseña</label>
                <div class="input-group">
                  <input
                    id="password"
                    v-model="password"
                    :type="showPassword ? 'text' : 'password'"
                    class="form-control"
                    :class="{ 'is-invalid': error }"
                    placeholder="Contraseña"  
                    required
                  >
                  <button 
                    class="btn btn-outline-secondary" 
                    type="button"
                    @click="showPassword = !showPassword"
                  >
                    <i :class="showPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
                  </button>
                </div>
              </div>

              <button 
                type="submit" 
                class="btn btn-primary w-100 py-2"
                :disabled="loading"
              >
                <span v-if="!loading">Iniciar Sesión</span>
                <span v-else class="spinner-border spinner-border-sm" role="status"></span>
              </button>

              <div v-if="error" class="alert alert-danger mt-3 mb-0">
                {{ error }}
              </div>
            </form>

            <hr class="my-4">
            <p class="text-center text-muted mb-0">
              ¿No tienes cuenta? 
              <router-link to="/register" class="text-decoration-none">
                Regístrate
              </router-link>
            </p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useAuthStore } from '../store/auth'

const username = ref('')
const password = ref('')
const showPassword = ref(false)
const error = ref('')
const loading = ref(false)

const auth = useAuthStore()
const router = useRouter()

const handleLogin = async () => {
  try {
    loading.value = true
    error.value = ''
    
    await auth.login(username.value, password.value)
    router.push('/dashboard')
  } catch (err) {
    error.value = 'Credenciales incorrectas. Por favor, intente nuevamente.'
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.card {
  border: none;
  border-radius: 10px;
}

.input-group-text {
  cursor: pointer;
}
</style>