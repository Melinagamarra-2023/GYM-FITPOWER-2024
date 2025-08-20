<template>
  <div class="container">
    <div class="row g-4">
      <div class="col-12">
        <div class="card shadow">
          <div class="card-header text-white">
            <h4 class="mb-0">
              <i class="bi bi-building-add me-2"></i>{{ isEdit ? 'Editar Gimnasio' : 'Crear Gimnasio' }}
            </h4>
          </div>
          <div class="card-body">
            <form @submit.prevent="onSubmit" class="needs-validation" novalidate>
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="address" class="form-label">Dirección *</label>
                  <input
                    type="text"
                    class="form-control"
                    id="address"
                    v-model="form.address"
                    :disabled="isEdit"
                    required
                  />
                  <div class="invalid-feedback">Por favor ingrese una dirección.</div>
                </div>
                <div class="col-md-6 mb-3">
                  <label for="domain" class="form-label">Nombre  *</label>
                  <input
                    type="text"
                    class="form-control"
                    id="domain"
                    v-model="form.domain"
                    required
                  />
                  <div class="invalid-feedback">Por favor ingrese un nombre.</div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="mail" class="form-label">Email *</label>
                  <input
                    type="email"
                    class="form-control"
                    id="mail"
                    v-model="form.mail"
                    required
                  />
                  <div class="invalid-feedback">Por favor ingrese un email válido.</div>
                </div>
                <div class="col-md-6 mb-3">
                  <label for="phone" class="form-label">Teléfono *</label>
                  <input
                    type="tel"
                    class="form-control"
                    id="phone"
                    v-model="form.phone"
                    required
                  />
                  <div class="invalid-feedback">Por favor ingrese un teléfono.</div>
                </div>
              </div>

              <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button
                  v-if="isEdit"
                  type="button"
                  class="btn btn-secondary me-md-2"
                  @click="onCancelEdit"
                >
                  <i class="bi bi-x-circle me-2"></i>Cancelar
                </button>
                <button type="submit" class="btn btn-primary btn_crear" :disabled="isSubmitting">
                  <i class="bi bi-check-circle me-2"></i>
                  {{ isSubmitting ? (isEdit ? 'Guardando...' : 'Creando...') : (isEdit ? 'Guardar Cambios' : 'Crear Gimnasio') }}
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div class="col-12">
        <div class="card shadow">
          <div class="card-header text-white">
            <h4 class="mb-0">
              <i class="bi bi-list-ul me-2"></i>Gimnasios
            </h4>
          </div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-hover align-middle">
                <thead>
                  <tr>
                    <th>Dirección</th>
                    <th>Nombre</th>
                    <th>Email</th>
                    <th>Teléfono</th>
                    <th>Estado</th>
                    <th class="text-end">Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="gym in gyms" :key="gym.address">
                    <td>{{ gym.address }}</td>
                    <td>{{ gym.domain }}</td>
                    <td>{{ gym.mail }}</td>
                    <td>{{ gym.phone }}</td>
                    <td>
                      <span class="badge" :class="gym.enabled ? 'bg-success' : 'bg-secondary'">
                        {{ gym.enabled ? 'Habilitado' : 'Deshabilitado' }}
                      </span>
                    </td>
                    <td class="text-end">
                      <button class="btn btn-sm btn-outline-primary me-2" @click="onEditGym(gym)">
                        <i class="bi bi-pencil"></i>
                      </button>
                      <button
                        class="btn btn-sm"
                        :class="gym.enabled ? 'btn-outline-danger' : 'btn-outline-success'"
                        @click="onToggleEnabled(gym)"
                      >
                        <i :class="gym.enabled ? 'bi bi-slash-circle' : 'bi bi-check-circle'" />
                      </button>
                    </td>
                  </tr>
                  <tr v-if="gyms.length === 0">
                    <td colspan="6" class="text-center text-muted">No hay gimnasios cargados.</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <transition name="fade">
      <div
        v-if="showToast"
        class="custom-toast"
        :class="{
          'toast-success': toastType === 'success',
          'toast-error': toastType === 'error',
        }"
      >
        <i :class="toastIcon" class="me-2"></i>
        <span>{{ toastMessage }}</span>
      </div>
    </transition>
  </div>
  
</template>

<script setup>
import { ref, reactive, onMounted, computed } from "vue";
import { useAuthStore } from "../store/auth";

const auth = useAuthStore();
const gyms = ref([]);
const isSubmitting = ref(false);
const isEdit = ref(false);
const originalAddress = ref("");

const form = reactive({
  address: "",
  domain: "",
  mail: "",
  phone: "",
});

const showToast = ref(false);
const toastMessage = ref("");
const toastType = ref("");
const toastIcon = computed(() => {
  return toastType.value === "success"
    ? "bi bi-check-circle-fill"
    : "bi bi-exclamation-triangle-fill";
});

function showToastMessage(message, type = "success") {
  toastMessage.value = message;
  toastType.value = type;
  showToast.value = true;
  setTimeout(() => {
    showToast.value = false;
  }, 3000);
}

const loadGyms = async () => {
  try {
    const token = auth.token;
    const response = await fetch("/api/V1/gyms", {
      method: "GET",
      headers: { Authorization: `Bearer ${token}` },
    });
    if (response.ok) {
      gyms.value = await response.json();
    } else {
      console.error("Error al cargar gimnasios", response.status, response.statusText);
      showToastMessage("Error al cargar gimnasios", "error");
    }
  } catch (e) {
    console.error("Error de conexión al cargar gimnasios", e);
    showToastMessage("Error de conexión al cargar gimnasios", "error");
  }
};

const resetForm = () => {
  form.address = "";
  form.domain = "";
  form.mail = "";
  form.phone = "";
  isEdit.value = false;
  originalAddress.value = "";
};

const onEditGym = (gym) => {
  isEdit.value = true;
  form.address = gym.address;
  form.domain = gym.domain;
  form.mail = gym.mail;
  form.phone = gym.phone;
  originalAddress.value = gym.address;
};

const onCancelEdit = () => {
  resetForm();
};

const onSubmit = async () => {
  const formEl = document.querySelector(".needs-validation");
  if (!formEl.checkValidity()) {
    formEl.classList.add("was-validated");
    return;
  }
  isSubmitting.value = true;
  try {
    const token = auth.token;
    if (!isEdit.value) {
      const response = await fetch("/api/V1/gyms", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(form),
      });
      if (!response.ok) {
        const err = await response.text();
        throw new Error(err || "Error al crear gimnasio");
      }
      showToastMessage("Gimnasio creado correctamente", "success");
    } else {
      const response = await fetch(`/api/V1/gyms/${encodeURIComponent(originalAddress.value)}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(form),
      });
      if (!response.ok) {
        const err = await response.text();
        throw new Error(err || "Error al actualizar gimnasio");
      }
      showToastMessage("Gimnasio actualizado correctamente", "success");
    }
    resetForm();
    await loadGyms();
  } catch (e) {
    console.error(e);
    showToastMessage(e.message || "Error en la operación", "error");
  } finally {
    isSubmitting.value = false;
  }
};

const onToggleEnabled = async (gym) => {
  try {
    const token = auth.token;
    const method = gym.enabled ? "DELETE" : "PATCH";
    const response = await fetch(`/api/V1/gyms/${encodeURIComponent(gym.address)}`, {
      method,
      headers: { Authorization: `Bearer ${token}` },
    });
    if (!response.ok) {
      const err = await response.text();
      throw new Error(err || "Error al cambiar estado");
    }
    showToastMessage(gym.enabled ? "Gimnasio deshabilitado" : "Gimnasio habilitado", "success");
    await loadGyms();
  } catch (e) {
    console.error(e);
    showToastMessage(e.message || "Error al cambiar estado", "error");
  }
};

onMounted(() => {
  loadGyms();
});
</script>

<style scoped>
.card {
  border: none;
  border-radius: 15px;
}

.form-control:focus,
.form-select:focus {
  border-color: #0d6efd;
  box-shadow: 0 0 0 0.2rem rgba(13, 110, 253, 0.25);
}

.btn {
  border-radius: 8px;
  font-weight: 500;
}

.card-header{
  background-color: #0a2d62;
}
.btn_crear{
  background-color: #0a2d62;
}

.custom-toast {
  position: fixed;
  top: 30px;
  left: 50%;
  transform: translateX(-50%);
  z-index: 9999;
  min-width: 250px;
  max-width: 90vw;
  background: #fff;
  color: #333;
  border-radius: 8px;
  box-shadow: 0 4px 24px rgba(0, 0, 0, 0.15);
  padding: 1rem 2rem;
  display: flex;
  align-items: center;
  font-size: 1.1rem;
  opacity: 0.97;
  border-left: 6px solid #28a745;
}
.toast-success { border-left-color: #28a745; }
.toast-error { border-left-color: #dc3545; }
.fade-enter-active, .fade-leave-active { transition: opacity 0.5s; }
.fade-enter, .fade-leave-to { opacity: 0; }
</style>


