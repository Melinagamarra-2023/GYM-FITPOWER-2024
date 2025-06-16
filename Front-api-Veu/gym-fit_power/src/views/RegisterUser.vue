<template>
  <div class="register-user">
    <div class="register-wrapper">
      <div class="card p-4 shadow-lg border-0">
        <h2 class="mb-4 text-center text-primary fw-bold">
          Registro de Usuario
        </h2>
        <form @submit.prevent="registerUser">
          <div class="mb-3">
            <label class="form-label">Email:</label>
            <input
              v-model="form.email"
              type="email"
              class="form-control"
              required
              placeholder="ejemplo@email.com"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Contraseña:</label>
            <input
              v-model="form.password"
              type="password"
              class="form-control"
              required
              placeholder="********"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Nombre:</label>
            <input
              v-model="form.name"
              type="text"
              class="form-control"
              required
              placeholder="Nombre"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Apellido:</label>
            <input
              v-model="form.lastname"
              type="text"
              class="form-control"
              required
              placeholder="Apellido"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">CUIT:</label>
            <input
              v-model="form.cuit"
              type="text"
              class="form-control"
              required
              placeholder="20-12345678-9"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Teléfono:</label>
            <input
              v-model="form.phone"
              type="text"
              class="form-control"
              required
              placeholder="11 1234-5678"
            />
          </div>
          <div class="mb-3">
            <label class="form-label">Rol:</label>
            <select v-model="form.roles" multiple class="form-select" required>
              <option value="ADMIN">ADMIN</option>
              <option value="USER">USER</option>
            </select>
            <small class="text-muted">Ctrl+Click para seleccionar varios</small>
          </div>
          <button type="submit" class="btn btn-gradient w-100 py-2 fw-bold">
            Registrar
          </button>
        </form>
        <div
          v-if="message"
          class="alert mt-3"
          :class="{
            'alert-success': message.includes('exitosamente'),
            'alert-danger': !message.includes('exitosamente'),
          }"
        >
          {{ message }}
        </div>
      </div>
    </div>
  </div>
  
</template>

<script>
export default {
  name: "RegisterUser",
  data() {
    return {
      form: {
        email: "",
        password: "",
        name: "",
        lastname: "",
        cuit: "",
        phone: "",
        roles: [],
      },
      message: "",
    };
  },
  methods: {
    async checkUserExists(field, value) {
      try {
        const response = await fetch(
          `http://localhost:8080/api/v1/auth/check?${field}=${value}`
        );
        return response.ok;
      } catch (error) {
        console.error("Error verificando usuario:", error);
        return false;
      }
    },

    async registerUser() {
      try {
        this.message = ""; // Limpiar mensaje anterior

        const response = await fetch(
          "http://localhost:8080/api/v1/auth/register",
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
            body: JSON.stringify(this.form),
          }
        );

        const data = await response.json();

        if (!response.ok) {
          // Manejar errores específicos del backend
          if (data.message.includes("CUIT") || data.message.includes("email")) {
            throw new Error(data.message);
          }
          throw new Error(data.message || "Error en el registro");
        }

        this.message = "Usuario registrado exitosamente";
        // Resetear formulario
        this.form = {
          email: "",
          password: "",
          name: "",
          lastname: "",
          cuit: "",
          phone: "",
          roles: [],
        };
      } catch (error) {
        this.message = error.message;
        console.error("Error de registro:", error);
      }
    },
  },
};
</script>

<style scoped>
.register-user {
  min-height: 100vh;
  background: linear-gradient(135deg, #e0eafc 0%, #cfdef3 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 0;
}
.register-wrapper {
  width: 100%;
  max-width: 420px;
  margin: 32px auto;
  padding: 16px;
  box-sizing: border-box;
}
.card {
  border-radius: 1.5rem;
  background: #fff;
  padding: 2rem !important;
  box-sizing: border-box;
}
.btn-gradient {
  background: linear-gradient(90deg, #007bff 0%, #00c6ff 100%);
  color: #fff;
  border: none;
  transition: background 0.3s;
}
.btn-gradient:hover {
  background: linear-gradient(90deg, #0056b3 0%, #007bff 100%);
  color: #fff;
}
@media (max-width: 575.98px) {
  .register-wrapper {
    max-width: 100%;
    padding: 0;
    margin: 0;
  }
  .card {
    border-radius: 0;
    padding: 1rem !important;
    box-shadow: none;
  }
}
</style>
