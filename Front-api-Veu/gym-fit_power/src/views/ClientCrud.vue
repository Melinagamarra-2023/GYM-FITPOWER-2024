<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <div class="card shadow">
          <div class="card-header text-white">
            <h4 class="mb-0">
              <i class="bi bi-person-plus me-2"></i>{{ isEdit ? 'Editar Cliente' : 'Crear Nuevo Cliente' }}
            </h4>
          </div>
          <div class="card-body">
            <form
              @submit.prevent="createClient"
              class="needs-validation"
              novalidate
            >
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="cuit" class="form-label">CUIL *</label>
                  <input
                    type="text"
                    class="form-control"
                    id="cuit"
                    v-model="clientForm.cuit"
                    required
                    maxlength="13"
                    @input="onCuitInput"
                    :readonly="isEdit"
                    :class="{ 'bg-light': isEdit }"
                  />
                  <div class="invalid-feedback">
                    Por favor ingrese un CUIL válido (ej: 20-12345678-9).
                  </div>
                </div>

                <div class="col-md-6 mb-3">
                  <label for="assignedGym" class="form-label"
                    >Gimnasio Asignado *</label
                  >
                  <select
                    class="form-select"
                    id="assignedGym"
                    v-model="clientForm.assignedGym"
                    required
                  >
                    <option value="">
                      Seleccione una dirección de una sucursal
                    </option>
                    <option
                      v-for="gym in gyms"
                      :key="gym.address"
                      :value="gym.address"
                    >
                      {{ gym.address }}
                    </option>
                  </select>
                  <div class="invalid-feedback">
                    Por favor seleccione una sucursal .
                  </div>
                </div>
              </div>

              <!-- Asignaciones de Entrenador y Nutricionista -->
              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="assignedTrainer" class="form-label">Entrenador Asignado</label>
                  <select
                    class="form-select"
                    id="assignedTrainer"
                    v-model="clientForm.assignedTrainer"
                    :disabled="isLoadingTrainers"
                  >
                    <option value="">
                      {{ isLoadingTrainers ? 'Cargando entrenadores...' : 'Seleccione un entrenador' }}
                    </option>
                    <option
                      v-for="trainer in trainers"
                      :key="trainer.cuit"
                      :value="trainer.cuit"
                    >
                      {{ trainer.name }} {{ trainer.lastname }} ({{ trainer.cuit }})
                    </option>
                  </select>
                  <div v-if="isLoadingTrainers" class="form-text text-muted">
                    <i class="bi bi-arrow-clockwise spin me-1"></i>Cargando entrenadores...
                  </div>
                  <div v-else-if="trainers.length === 0" class="form-text text-muted">
                    No hay entrenadores disponibles
                    <button 
                      type="button" 
                      class="btn btn-sm btn-outline-secondary ms-2"
                      @click="loadTrainers"
                      :disabled="isLoadingTrainers"
                    >
                      <i class="bi bi-arrow-clockwise me-1"></i>Reintentar
                    </button>
                  </div>
                </div>

                <div class="col-md-6 mb-3">
                  <label for="assignedNutritionist" class="form-label">Nutricionista Asignado</label>
                  <select
                    class="form-select"
                    id="assignedNutritionist"
                    v-model="clientForm.assignedNutritionist"
                    :disabled="isLoadingNutritionists"
                  >
                    <option value="">
                      {{ isLoadingNutritionists ? 'Cargando nutricionistas...' : 'Seleccione un nutricionista' }}
                    </option>
                    <option
                      v-for="nutri in nutritionists"
                      :key="nutri.cuit"
                      :value="nutri.cuit"
                    >
                      {{ nutri.name }} {{ nutri.lastname }} ({{ nutri.cuit }})
                    </option>
                  </select>
                  <div v-if="isLoadingNutritionists" class="form-text text-muted">
                    <i class="bi bi-arrow-clockwise spin me-1"></i>Cargando nutricionistas...
                  </div>
                  <div v-else-if="nutritionists.length === 0" class="form-text text-muted">
                    No hay nutricionistas disponibles
                    <button 
                      type="button" 
                      class="btn btn-sm btn-outline-secondary ms-2"
                      @click="loadNutritionists"
                      :disabled="isLoadingNutritionists"
                    >
                      <i class="bi bi-arrow-clockwise me-1"></i>Reintentar
                    </button>
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="name" class="form-label">Nombre *</label>
                  <input
                    type="text"
                    class="form-control"
                    id="name"
                    v-model="clientForm.name"
                    required
                  />
                  <div class="invalid-feedback">
                    Por favor ingrese el nombre.
                  </div>
                </div>

                <div class="col-md-6 mb-3">
                  <label for="lastname" class="form-label">Apellido *</label>
                  <input
                    type="text"
                    class="form-control"
                    id="lastname"
                    v-model="clientForm.lastname"
                    required
                  />
                  <div class="invalid-feedback">
                    Por favor ingrese el apellido.
                  </div>
                </div>
              </div>

              <div class="row">
                <div class="col-md-6 mb-3">
                  <label for="email" class="form-label">Email *</label>
                  <input
                    type="email"
                    class="form-control"
                    id="email"
                    v-model="clientForm.email"
                    required
                  />
                  <div class="invalid-feedback">
                    Por favor ingrese un email válido.
                  </div>
                </div>

                <div class="col-md-6 mb-3">
                  <label for="phone" class="form-label">Teléfono *</label>
                  <input
                    type="tel"
                    class="form-control"
                    id="phone"
                    v-model="clientForm.phone"
                    required
                    maxlength="10"
                    @input="onPhoneInput"
                  />
                  <div class="invalid-feedback">
                    Por favor ingrese un teléfono válido de 10 dígitos.
                  </div>
                </div>
              </div>

              <div class="mb-3">
                <label for="birthDate" class="form-label"
                  >Fecha de Nacimiento *</label
                >
                <input
                  type="date"
                  class="form-control"
                  id="birthDate"
                  v-model="clientForm.birthDate"
                  required
                />
                <div class="invalid-feedback">
                  Por favor ingrese la fecha de nacimiento.
                </div>
              </div>

              <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <button
                  type="button"
                  class="btn btn-secondary me-md-2"
                  @click="onCancelEdit"
                >
                  <i :class="['bi', 'me-2', isEdit ? 'bi-x-circle' : 'bi-arrow-clockwise']"></i>{{ isEdit ? 'Cancelar' : 'Limpiar' }}
                </button>
                <button
                  type="submit"
                  class="btn btn-primary btn_crear"
                  :disabled="isSubmitting"
                >
                  <i class="bi bi-check-circle me-2"></i>
                  {{ isSubmitting ? (isEdit ? 'Guardando...' : 'Creando...') : (isEdit ? 'Guardar Cambios' : 'Crear Cliente') }}
                </button>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>

    <div class="row mt-4">
      <div class="col-12">
        <div class="card shadow">
          <div class="card-header text-white">
            <h4 class="mb-0">
              <i class="bi bi-list-ul me-2"></i>Clientes
            </h4>
          </div>
          <div class="card-body">
            <div class="table-responsive">
              <table class="table table-hover align-middle">
                <thead>
                  <tr>
                    <th>CUIL</th>
                    <th>Nombre</th>
                    <th>Apellido</th>
                    <th>Email</th>
                    <th>Teléfono</th>
                    <th>Gimnasio</th>
                    <th>Entrenador</th>
                    <th>Nutricionista</th>
                    <th>Estado</th>
                    <th class="text-end">Acciones</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="client in clients" :key="client.cuit">
                    <td>{{ client.cuit }}</td>
                    <td>{{ client.name }}</td>
                    <td>{{ client.lastname }}</td>
                    <td>{{ client.email }}</td>
                    <td>{{ client.phone }}</td>
                    <td>{{ client.assignedGym }}</td>
                    <td>{{ client.assignedTrainer || 'No asignado' }}</td>
                    <td>{{ client.assignedNutritionist || 'No asignado' }}</td>
                    <td>
                      <span class="badge" :class="client.enabled ? 'bg-success' : 'bg-secondary'">
                        {{ client.enabled ? 'Habilitado' : 'Deshabilitado' }}
                      </span>
                    </td>
                    <td class="text-end">
                      <button 
                        class="btn btn-sm btn-outline-primary me-2" 
                        @click="onEditClient(client)"
                        :title="'Editar ' + client.name + ' ' + client.lastname"
                      >
                        <i class="bi bi-pencil"></i>
                      </button>
                      <button
                        class="btn btn-sm"
                        :class="client.enabled ? 'btn-outline-danger' : 'btn-outline-success'"
                        @click="onToggleEnabled(client)"
                        :title="client.enabled ? 'Deshabilitar cliente' : 'Habilitar cliente'"
                      >
                        <i :class="client.enabled ? 'bi bi-slash-circle' : 'bi bi-check-circle'" />
                      </button>
                    </td>
                  </tr>
                  <tr v-if="clients.length === 0">
                    <td colspan="10" class="text-center text-muted">No hay clientes cargados.</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- Alertas -->
    <div v-if="alert.show" class="alert mt-3" :class="alert.class" role="alert">
      <i :class="alert.icon" class="me-2"></i>
      {{ alert.message }}
    </div>

    <!-- Toast de feedback -->
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
const isSubmitting = ref(false);
const isEdit = ref(false);
const originalCuit = ref("");
const gyms = ref([]);
const clients = ref([]);
const trainers = ref([]);
const nutritionists = ref([]);

const isLoadingTrainers = ref(false);
const isLoadingNutritionists = ref(false);

// Emitir eventos al componente padre   
// eslint-disable-next-line no-undef
const emit = defineEmits(["client-created", "client-error"]);

// Formulario del cliente
const clientForm = reactive({
  cuit: "",
  assignedGym: "",
  assignedTrainer: "",
  assignedNutritionist: "",
  name: "",
  lastname: "",
  email: "",
  phone: "",
  birthDate: "",
});

// Estado de alertas
const alert = reactive({
  show: false,
  message: "",
  class: "",
  icon: "",
});

// Toast states
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

// Cargar gimnasios disponibles
const loadGyms = async () => {
  try {
    const token = auth.token;
    const response = await fetch("/api/V1/gyms", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const gymsData = await response.json();
      gyms.value = gymsData;
    } else {
      console.error(
        "Error al cargar gimnasios:",
        response.status,
        response.statusText
      );
      showAlert("Error al cargar los gimnasios disponibles", "error");
    }
  } catch (error) {
    console.error("Error de conexión al cargar gimnasios:", error);
    showAlert("Error de conexión al cargar gimnasios", "error");
  }
};

// Cargar clientes
const loadClients = async () => {
  try {
    const token = auth.token;
    const response = await fetch("/api/v1/clients", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    if (response.ok) {
      const clientsData = await response.json();
      clients.value = clientsData;
    } else {
      console.error("❌ Error al cargar clientes:", response.status, response.statusText);
      showToastMessage("Error al cargar clientes", "error");
    }
  } catch (error) {
    console.error("❌ Error de conexión al cargar clientes:", error);
    showToastMessage("Error de conexión al cargar clientes", "error");
  }
};

// Cargar entrenadores disponibles
const loadTrainers = async () => {
  try {
    isLoadingTrainers.value = true;
    const token = auth.token;
    const response = await fetch("/api/v1/trainer", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const trainersData = await response.json();
      trainers.value = trainersData || [];
    } else {
      console.error(
        "Error al cargar entrenadores:",
        response.status,
        response.statusText
      );
      if (response.status === 403) {
        showToastMessage("No tiene permisos para ver entrenadores", "error");
      } else {
        showToastMessage("Error al cargar entrenadores", "error");
      }
    }
  } catch (error) {
    console.error("Error de conexión al cargar entrenadores:", error);
    showToastMessage("Error de conexión al cargar entrenadores", "error");
  } finally {
    isLoadingTrainers.value = false;
  }
};

// Cargar nutricionistas disponibles
const loadNutritionists = async () => {
  try {
    isLoadingNutritionists.value = true;
    const token = auth.token;
    const response = await fetch("/api/v1/Nutritionist/readAll", {
      method: "GET",
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    if (response.ok) {
      const nutriData = await response.json();
      nutritionists.value = nutriData || [];
    } else {
      console.error(
        "Error al cargar nutricionistas:",
        response.status,
        response.statusText
      );
      if (response.status === 403) {
        showToastMessage("No tiene permisos para ver nutricionistas", "error");
      } else {
        showToastMessage("Error al cargar nutricionistas", "error");
      }
    }
  } catch (error) {
    console.error("Error de conexión al cargar nutricionistas:", error);
    showToastMessage("Error de conexión al cargar nutricionistas", "error");
  } finally {
    isLoadingNutritionists.value = false;
  }
};

const resetForm = () => {
  Object.keys(clientForm).forEach((key) => {
    clientForm[key] = "";
  });
  isEdit.value = false;
  originalCuit.value = "";
  hideAlert();
};

const showAlert = (message, type = "success") => {
  alert.show = true;
  alert.message = message;

  switch (type) {
    case "success":
      alert.class = "alert-success";
      alert.icon = "bi bi-check-circle-fill";
      setTimeout(hideAlert, 5000);
      break;
    case "error":
      alert.class = "alert-danger";
      alert.icon = "bi bi-exclamation-triangle-fill";
      setTimeout(hideAlert, 5000);
      break;
    case "warning":
      alert.class = "alert-warning";
      alert.icon = "bi bi-exclamation-circle-fill";
      setTimeout(hideAlert, 5000);
      break;
  }
};

const hideAlert = () => {
  alert.show = false;
};

const createClient = async () => {
  // Validación del formulario
  const form = document.querySelector(".needs-validation");
  if (!form.checkValidity()) {
    form.classList.add("was-validated");
    return;
  }

  isSubmitting.value = true;
  hideAlert();

  try {
    const token = auth.token;
    const payload = {
      cuit: clientForm.cuit,
      assignedGym: clientForm.assignedGym,
      assignedTrainer: clientForm.assignedTrainer || null,
      assignedNutritionist: clientForm.assignedNutritionist || null,
      name: clientForm.name,
      lastname: clientForm.lastname,
      email: clientForm.email,
      phone: clientForm.phone,
      birthDate: clientForm.birthDate,
    };

    let response;
    if (!isEdit.value) {
      response = await fetch("/api/v1/clients/create", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });
    } else {
      response = await fetch(`/api/v1/clients/${encodeURIComponent(originalCuit.value)}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(payload),
      });
    }

    if (response.ok) {
      const client = await response.json();
      showToastMessage(
        !isEdit.value
          ? `Cliente ${client.name} ${client.lastname} creado exitosamente!`
          : `Cliente ${client.name} ${client.lastname} actualizado exitosamente!`,
        "success"
      );
      await loadClients();
      setTimeout(() => {
        resetForm();
      }, 3000);
      emit("client-created", client);
    } else {
      let errorMessage = "Error en la operación";
      try {
        const errorData = await response.json();
        errorMessage = `Error: ${errorData.message || "Error desconocido"}`;
      } catch (parseError) {
        console.warn("No se pudo parsear el error como JSON", parseError);
      }
      showToastMessage(errorMessage, "error");
      emit("client-error", errorMessage);
    }
  } catch (error) {
    console.error("Error:", error);
    const errorMessage = "Error de conexión. Por favor, intente nuevamente.";
    showToastMessage(errorMessage, "error");
    emit("client-error", errorMessage);
  } finally {
    isSubmitting.value = false;
  }
};

// Cargar gimnasios al montar el componente
onMounted(() => {
  loadGyms();
  loadClients();
  loadTrainers();
  loadNutritionists();
});

// Watcher para debuggear cambios en la lista de clientes
import { watch } from "vue";
watch(clients, (newClients) => {
  newClients;
}, { deep: true });

const formatCuit = (value) => {
  let digits = value.replace(/\D/g, '');
  if (digits.length > 2) digits = digits.slice(0, 2) + '-' + digits.slice(2);
  if (digits.length > 11) digits = digits.slice(0, 11) + '-' + digits.slice(11, 12);
  if (digits.length > 13) digits = digits.slice(0, 13);
  return digits;
};

const onlyNumbers = (value) => value.replace(/\D/g, '').slice(0, 10);

const onCuitInput = (e) => {
  clientForm.cuit = formatCuit(e.target.value);
};

const onPhoneInput = (e) => {
  clientForm.phone = onlyNumbers(e.target.value);
};

const onEditClient = (client) => {
  isEdit.value = true;
  originalCuit.value = client.cuit;
  clientForm.cuit = client.cuit;
  clientForm.assignedGym = client.assignedGym;
  clientForm.assignedTrainer = client.assignedTrainer || "";
  clientForm.assignedNutritionist = client.assignedNutritionist || "";
  clientForm.name = client.name;
  clientForm.lastname = client.lastname;
  clientForm.email = client.email;
  clientForm.phone = client.phone;
  clientForm.birthDate = client.birthDate;
};

const onCancelEdit = () => {
  resetForm();
};

const onToggleEnabled = async (client) => {
  try {
    const token = auth.token;
    const method = client.enabled ? "DELETE" : "PATCH";
    const url = `/api/v1/clients/${encodeURIComponent(client.cuit)}`;
    
    
    const response = await fetch(url, {
      method,
      headers: { Authorization: `Bearer ${token}` },
    });
    
    
    if (!response.ok) {
      const err = await response.text();
      console.error("❌ Error en respuesta:", err);
      throw new Error(err || "Error al cambiar estado");
    }
    
    showToastMessage(client.enabled ? "Cliente deshabilitado" : "Cliente habilitado", "success");
    
    await loadClients();
    
  } catch (e) {
    console.error("❌ Error en onToggleEnabled:", e);
    showToastMessage(e.message || "Error al cambiar estado", "error");
  }
};
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

.form-control[readonly] {
  background-color: #f8f9fa;
  cursor: not-allowed;
  opacity: 0.8;
}

.badge {
  font-size: 0.75rem;
  padding: 0.5em 0.75em;
}



.btn {
  border-radius: 8px;
  font-weight: 500;
}

.alert {
  border-radius: 10px;
  border: none;
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
.toast-success {
  border-left-color: #28a745;
}
.toast-error {
  border-left-color: #dc3545;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
