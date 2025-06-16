<template>
  <div class="dashboard d-flex min-vh-100">
    <aside
      class="sidebar bg-dark text-white p-4 d-flex flex-column align-items-center"
      :class="{ collapsed: isCollapsed }"
      @mouseenter="isCollapsed = false"
      @mouseleave="isCollapsed = true"
    >
      <!-- Elimina el botón de toggle -->
      <h2 class="text-center mb-4" v-if="!isCollapsed">
        <i class="bi bi-speedometer2 me-2"></i>Menú
      </h2>
      <ul class="nav flex-column w-100">
        <li
          class="nav-item mb-2"
          :class="{ 'bg-secondary rounded': activeTab === 'home' }"
        >
          <a
            class="nav-link text-white d-flex align-items-center"
            href="#"
            @click.prevent="activeTab = 'home'"
          >
            <i class="bi bi-house-door me-2"></i>
            <span v-if="!isCollapsed">Inicio</span>
          </a>
        </li>
        <li
          class="nav-item mb-2"
          :class="{ 'bg-secondary rounded': activeTab === 'profile' }"
        >
          <a
            class="nav-link text-white d-flex align-items-center"
            href="#"
            @click.prevent="activeTab = 'profile'"
          >
            <i class="bi bi-person me-2"></i>
            <span v-if="!isCollapsed">Perfil</span>
          </a>
        </li>
        <li
          class="nav-item mb-2"
          :class="{ 'bg-secondary rounded': activeTab === 'settings' }"
        >
          <a
            class="nav-link text-white d-flex align-items-center"
            href="#"
            @click.prevent="activeTab = 'settings'"
          >
            <i class="bi bi-gear me-2"></i>
            <span v-if="!isCollapsed">Configuración</span>
          </a>
        </li>
        <li class="nav-item mt-4">
          <a
            class="nav-link text-danger d-flex align-items-center"
            href="#"
            @click.prevent="logout"
          >
            <i class="bi bi-box-arrow-right me-2"></i>
            <span v-if="!isCollapsed">Cerrar sesión</span>
          </a>
        </li>
      </ul>
    </aside>
    <main class="content flex-grow-1 p-4 bg-light">
      <div v-if="activeTab === 'home'">
        <h1>
          <i class="bi bi-house-door me-2"></i>Bienvenido al Dashboard
        </h1>
        <p>¡Estás autenticado!</p>
      </div>
      <div v-else-if="activeTab === 'profile'">
        <h1>
          <i class="bi bi-person me-2"></i>Perfil
        </h1>
        <p>Aquí puedes ver y editar tu perfil.</p>
      </div>
      <div v-else-if="activeTab === 'settings'">
        <h1>
          <i class="bi bi-gear me-2"></i>Configuración
        </h1>
        <p>Ajusta las preferencias de tu cuenta.</p>
      </div>
    </main>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useAuthStore } from '../store/auth'
import { useRouter } from 'vue-router'

const auth = useAuthStore()
const router = useRouter()
const activeTab = ref('home')
const isCollapsed = ref(true) // Empieza colapsado

const logout = () => {
  auth.logout()
  router.push('/login')
}
</script>

<style scoped>
.sidebar {
  min-width: 220px;
  max-width: 250px;
  transition: all 0.2s;
}
.sidebar.collapsed {
  min-width: 60px;
  max-width: 60px;
  padding-left: 0.5rem !important;
  padding-right: 0.5rem !important;
}
.nav-link {
  transition: background 0.2s;
}
.nav-link:hover,
.bg-secondary {
  background: #444 !important;
}
</style>
