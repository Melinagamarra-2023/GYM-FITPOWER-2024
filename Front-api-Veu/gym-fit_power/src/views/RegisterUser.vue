<template>
  <div class="register-user">
    <div class="register-wrapper">
      <div class="card p-4 shadow-lg border-0">
        <div class="text-center mb-4">
          <img
            src="@/assets/logo.png"
            alt="Logo"
            class="logo mb-3"
            v-if="logo"
          />
          <h2 class="text-primary fw-bold">Registro de Usuario</h2>
          <p class="text-muted">Complete sus datos para registrarse</p>
        </div>

        <form @submit.prevent="registerUser" novalidate>
          <!-- Email -->
          <div class="mb-3">
            <label for="email" class="form-label">Email:</label>
            <input
              id="email"
              v-model.trim="form.email"
              type="email"
              class="form-control"
              :class="{ 'is-invalid': errors.email }"
              required
              placeholder="ejemplo@email.com"
              @blur="validateEmail"
            />
            <div class="invalid-feedback">{{ errors.email }}</div>
          </div>

          <!-- Contraseña -->
          <div class="mb-3">
            <label for="password" class="form-label">Contraseña:</label>
            <div class="input-group">
              <input
                id="password"
                v-model.trim="form.password"
                :type="showPassword ? 'text' : 'password'"
                class="form-control"
                :class="{ 'is-invalid': errors.password }"
                required
                placeholder="Mínimo 8 caracteres"
                @input="validatePassword"
              />
              <button
                class="btn btn-outline-secondary"
                type="button"
                @click="showPassword = !showPassword"
              >
                <i :class="showPassword ? 'bi bi-eye-slash' : 'bi bi-eye'"></i>
              </button>
            </div>
            <div class="invalid-feedback">{{ errors.password }}</div>
            <div class="password-strength mt-1">
              <div class="strength-bar" :class="passwordStrengthClass"></div>
              <small class="text-muted">{{ passwordStrengthText }}</small>
            </div>
          </div>

          <!-- Nombre y Apellido -->
          <div class="row">
            <div class="col-md-6 mb-3">
              <label for="name" class="form-label">Nombre:</label>
              <input
                id="name"
                v-model.trim="form.name"
                type="text"
                class="form-control"
                :class="{ 'is-invalid': errors.name }"
                required
                placeholder="Nombre"
                @blur="validateName"
              />
              <div class="invalid-feedback">{{ errors.name }}</div>
            </div>
            <div class="col-md-6 mb-3">
              <label for="lastname" class="form-label">Apellido:</label>
              <input
                id="lastname"
                v-model.trim="form.lastname"
                type="text"
                class="form-control"
                :class="{ 'is-invalid': errors.lastname }"
                required
                placeholder="Apellido"
                @blur="validateLastname"
              />
              <div class="invalid-feedback">{{ errors.lastname }}</div>
            </div>
          </div>

          <!-- CUIT -->
          <div class="mb-3">
            <label for="cuit" class="form-label">CUIT:</label>
            <input
              id="cuit"
              v-model.trim="form.cuit"
              type="text"
              class="form-control"
              :class="{ 'is-invalid': errors.cuit }"
              required
              placeholder="20-12345678-9"
              @input="formatCuit"
              @blur="validateCuit"
            />
            <div class="invalid-feedback">{{ errors.cuit }}</div>
          </div>

          <!-- Teléfono -->
          <div class="mb-3">
            <label for="phone" class="form-label">Teléfono:</label>
            <input
              id="phone"
              v-model.trim="form.phone"
              type="tel"
              class="form-control"
              :class="{ 'is-invalid': errors.phone }"
              required
              placeholder="3764 34-5678"
              @input="formatPhone"
              @blur="validatePhone"
            />
            <div class="invalid-feedback">{{ errors.phone }}</div>
          </div>

          <!-- Roles -->
          <div class="mb-4">
            <label class="form-label">Rol:</label>
            <div class="roles-container">
              <div
                v-for="role in availableRoles"
                :key="role.value"
                class="form-check form-check-inline"
              >
                <input
                  class="form-check-input"
                  type="checkbox"
                  :id="`role-${role.value}`"
                  :value="role.value"
                  v-model="form.roles"
                />
                <label class="form-check-label" :for="`role-${role.value}`">
                  {{ role.label }}
                </label>
              </div>
            </div>
            <small v-if="errors.roles" class="text-danger">{{
              errors.roles
            }}</small>
          </div>

          <button
            type="submit"
            class="btn btn-gradient w-100 py-2 fw-bold"
            :disabled="isSubmitting"
          >
            <span v-if="isSubmitting">
              <span
                class="spinner-border spinner-border-sm"
                role="status"
                aria-hidden="true"
              ></span>
              Procesando...
            </span>
            <span v-else>Registrarse</span>
          </button>

          <div class="text-center mt-3">
            <p>
              ¿Ya tienes una cuenta?
              <router-link to="/login">Inicia sesión</router-link>
            </p>
          </div>
        </form>

        <!-- Mensajes de feedback -->
        <transition name="fade">
          <div
            v-if="message"
            class="alert mt-3 mb-0"
            :class="{
              'alert-success': messageType === 'success',
              'alert-danger': messageType === 'error',
            }"
          >
            <div class="d-flex align-items-center">
              <i :class="messageIcon" class="me-2"></i>
              <span>{{ message }}</span>
            </div>
          </div>
        </transition>
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
      errors: {
        email: "",
        password: "",
        name: "",
        lastname: "",
        cuit: "",
        phone: "",
        roles: "",
      },
      availableRoles: [
        { value: "ADMIN", label: "Administrador" },
        { value: "USER", label: "Usuario" },
      ],
      termsAccepted: false,
      showPassword: false,
      isSubmitting: false,
      message: "",
      messageType: "",
      logo: true,
    };
  },
  computed: {
    passwordStrength() {
      if (!this.form.password) return 0;

      let strength = 0;

      // Longitud mínima
      if (this.form.password.length >= 8) strength += 1;

      // Contiene números
      if (/\d/.test(this.form.password)) strength += 1;

      // Contiene mayúsculas
      if (/[A-Z]/.test(this.form.password)) strength += 1;

      // Contiene caracteres especiales
      if (/[^A-Za-z0-9]/.test(this.form.password)) strength += 1;

      return strength;
    },
    passwordStrengthClass() {
      return [
        "strength-" + this.passwordStrength,
        { "bg-danger": this.passwordStrength <= 1 },
        { "bg-warning": this.passwordStrength === 2 },
        { "bg-info": this.passwordStrength === 3 },
        { "bg-success": this.passwordStrength >= 4 },
      ];
    },
    passwordStrengthText() {
      const texts = ["Muy débil", "Débil", "Moderada", "Fuerte", "Muy fuerte"];
      return texts[this.passwordStrength];
    },
    messageIcon() {
      return this.messageType === "success"
        ? "bi bi-check-circle-fill"
        : "bi bi-exclamation-triangle-fill";
    },
  },
  methods: {
    async registerUser() {
      if (!this.validateForm()) return;

      try {
        this.isSubmitting = true;
        this.message = "";

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
          throw new Error(data.message || "Error en el registro");
        }

        this.showMessage("Usuario registrado exitosamente", "success");
        this.resetForm();

        // Redirigir después de 2 segundos
        setTimeout(() => {
          this.$router.push("/login");
        }, 7000);
      } catch (error) {
        this.showMessage(error.message, "error");
        console.error("Error de registro:", error);
      } finally {
        this.isSubmitting = false;
      }
    },

    validateForm() {
      this.validateEmail();
      this.validatePassword();
      this.validateName();
      this.validateLastname();
      this.validateCuit();
      this.validatePhone();
      this.validateRoles();

      return !Object.values(this.errors).some((error) => error !== "");
    },

    validateEmail() {
      const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
      if (!this.form.email) {
        this.errors.email = "El email es requerido";
      } else if (!emailRegex.test(this.form.email)) {
        this.errors.email = "Por favor ingrese un email válido";
      } else {
        this.errors.email = "";
      }
    },

    validatePassword() {
      if (!this.form.password) {
        this.errors.password = "La contraseña es requerida";
      } else if (this.form.password.length < 8) {
        this.errors.password = "La contraseña debe tener al menos 8 caracteres";
      } else {
        this.errors.password = "";
      }
    },

    validateName() {
      if (!this.form.name) {
        this.errors.name = "El nombre es requerido";
      } else if (this.form.name.length < 2) {
        this.errors.name = "El nombre debe tener al menos 2 caracteres";
      } else {
        this.errors.name = "";
      }
    },

    validateLastname() {
      if (!this.form.lastname) {
        this.errors.lastname = "El apellido es requerido";
      } else if (this.form.lastname.length < 2) {
        this.errors.lastname = "El apellido debe tener al menos 2 caracteres";
      } else {
        this.errors.lastname = "";
      }
    },

    validateCuit() {
      const cuitRegex = /^(20|23|27|30|33)([0-9]{9}|-[0-9]{8}-[0-9]{1})$/;
      if (!this.form.cuit) {
        this.errors.cuit = "El CUIT es requerido";
      } else if (!cuitRegex.test(this.form.cuit)) {
        this.errors.cuit = "Por favor ingrese un CUIT válido";
      } else {
        this.errors.cuit = "";
      }
    },

    validatePhone() {
      const phoneRegex = /^(\d{2,4}[-\s]?){2}\d{4}$/;
      if (!this.form.phone) {
        this.errors.phone = "El teléfono es requerido";
      } else if (!phoneRegex.test(this.form.phone)) {
        this.errors.phone = "Por favor ingrese un teléfono válido";
      } else {
        this.errors.phone = "";
      }
    },

    validateRoles() {
      if (this.form.roles.length === 0) {
        this.errors.roles = "Debe seleccionar al menos un rol";
      } else {
        this.errors.roles = "";
      }
    },

    formatCuit() {
      // Eliminar todo lo que no sea número
      let value = this.form.cuit.replace(/\D/g, "");

      // Aplicar formato XX-XXXXXXXX-X
      if (value.length > 2) {
        value = value.substring(0, 2) + "-" + value.substring(2);
      }
      if (value.length > 11) {
        value = value.substring(0, 11) + "-" + value.substring(11, 12);
      }

      this.form.cuit = value;
    },

    formatPhone() {
      // Eliminar todo lo que no sea número
      let value = this.form.phone.replace(/\D/g, "");

      // Aplicar formato XX XXXX-XXXX
      if (value.length > 2) {
        value = value.substring(0, 2) + " " + value.substring(2);
      }
      if (value.length > 7) {
        value = value.substring(0, 7) + "-" + value.substring(7);
      }

      this.form.phone = value.substring(0, 12); // Limitar a 11 dígitos
    },

    showMessage(message, type) {
      this.message = message;
      this.messageType = type;

      // Auto-ocultar mensaje después de 5 segundos
      setTimeout(() => {
        if (this.message === message) {
          this.message = "";
        }
      }, 5000);
    },

    resetForm() {
      this.form = {
        email: "",
        password: "",
        name: "",
        lastname: "",
        cuit: "",
        phone: "",
        roles: [],
      };
      this.termsAccepted = false;
      this.errors = {
        email: "",
        password: "",
        name: "",
        lastname: "",
        cuit: "",
        phone: "",
        roles: "",
      };
    },
  },
};
</script>

<style scoped>
.register-user {
  min-height: 100vh;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 2rem 0;
}

.register-wrapper {
  width: 100%;
  max-width: 500px;
  margin: 0 auto;
  padding: 0 16px;
  box-sizing: border-box;
}

.card {
  border-radius: 1rem;
  background: #fff;
  padding: 2rem !important;
  box-shadow: 0 10px 30px rgba(0, 0, 0, 0.08);
}

.logo {
  max-height: 60px;
}

.btn-gradient {
  background: linear-gradient(90deg, #4b6cb7 0%, #182848 100%);
  color: #fff;
  border: none;
  transition: all 0.3s ease;
  position: relative;
  overflow: hidden;
}

.btn-gradient:hover {
  background: linear-gradient(90deg, #3a56a0 0%, #121d3a 100%);
  transform: translateY(-2px);
  box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
}

.btn-gradient:disabled {
  background: #cccccc;
  transform: none;
  box-shadow: none;
}

.password-strength {
  width: 100%;
}

.strength-bar {
  height: 4px;
  width: 100%;
  border-radius: 2px;
  margin-bottom: 2px;
  transition: all 0.3s ease;
}

.strength-0 {
  width: 20%;
  background-color: #dc3545;
}

.strength-1 {
  width: 40%;
  background-color: #fd7e14;
}

.strength-2 {
  width: 60%;
  background-color: #ffc107;
}

.strength-3 {
  width: 80%;
  background-color: #17a2b8;
}

.strength-4 {
  width: 100%;
  background-color: #28a745;
}

.roles-container {
  background: #f8f9fa;
  padding: 12px;
  border-radius: 8px;
  border: 1px solid #dee2e6;
}

.form-check-inline {
  margin-right: 1.5rem;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}

@media (max-width: 767.98px) {
  .register-user {
    padding: 1rem;
  }

  .card {
    padding: 1.5rem !important;
  }

  .logo {
    max-height: 50px;
  }
}
</style>
