package com.example.appapoyoemocional.network

// --- CORRECCIÓN 1: Añadir TODAS las importaciones necesarias ---
import com.example.appapoyoemocional.data.UserCreate
import com.example.appapoyoemocional.data.UserResponse
import retrofit2.Response // <-- Importante: para envolver la respuesta
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
// ----------------------------------------------------------------

interface UsuarioService {

    /**
     * Endpoint para registrar un nuevo usuario.
     * Envía los datos del usuario y espera una respuesta con los datos del usuario creado.
     */
    @POST("usuarios") // <-- CORRECCIÓN 2: La ruta debe coincidir con tu backend (ej. "usuarios" o "api/users")
    suspend fun registrarUsuario(@Body usuario: UserCreate): Response<UserResponse>

    /**
     * Endpoint para obtener el perfil de un usuario por su ID.
     */
    @GET("usuarios/{id}") // <-- CORRECCIÓN 3: La ruta debe coincidir con tu backend
    suspend fun obtenerPerfilDeUsuario(@Path("id") userId: Int): Response<UserResponse> // <-- CORRECCIÓN 4: Usamos UserResponse
}
