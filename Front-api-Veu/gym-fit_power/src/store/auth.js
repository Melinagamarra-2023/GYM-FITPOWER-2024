import { defineStore } from "pinia";
import axios from "axios";

// Define la tienda de autenticación usando Pinia
export const useAuthStore = defineStore("auth", {
    // Estado inicial de la tienda
    state: () => ({
        token: localStorage.getItem("token") || null, // Token de autenticación almacenado en localStorage
        user: null, // Información del usuario (puede ser llenada después)
    }),
    actions: {
        // Acción para iniciar sesión
        async login(email, password) {
            try {
                // Realiza la petición de login al backend
                const res = await axios.post(
                    "http://localhost:8080/api/v1/auth/login",
                    {
                        email,
                        password,
                    }
                );
                // Guarda el token recibido en el estado y en localStorage
                this.token = res.data.token;
                localStorage.setItem("token", this.token);
                // Establece el token en los headers de axios para futuras peticiones
                axios.defaults.headers.common["Authorization"] = `Bearer ${this.token}`;
            } catch (err) {
                // Lanza un error si el login falla
                throw new Error("Login inválido");
            }
        },
        // Acción para cerrar sesión
        logout() {
            this.token = null; // Limpia el token del estado
            this.user = null; // Limpia la información del usuario
            localStorage.removeItem("token"); // Elimina el token de localStorage
            delete axios.defaults.headers.common["Authorization"]; // Elimina el header de autorización de axios
        },
    },
});
