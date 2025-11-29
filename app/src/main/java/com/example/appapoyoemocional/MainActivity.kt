package com.example.appapoyoemocional

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appapoyoemocional.ui.theme.AppApoyoEmocionalTheme
// --- ¡IMPORTANTE! AÑADE ESTA IMPORTACIÓN ---
import com.example.appapoyoemocional.view.LoginScreen
// -----------------------------------------
import com.example.appapoyoemocional.view.EmocionGuardadaScreen
import com.example.appapoyoemocional.view.EmocionScreen
import com.example.appapoyoemocional.view.FormularioScreen
import com.example.appapoyoemocional.view.PaginaInicio
import com.example.appapoyoemocional.view.RecFacialScreen
import com.example.appapoyoemocional.view.RecursosScreen
import com.example.appapoyoemocional.view.RespiraScreen
import com.example.appapoyoemocional.view.ResumenScreen
import com.example.appapoyoemocional.view.screen.PerfilScreen
import com.example.appapoyoemocional.view.screen.PostScreen
// --- ¡IMPORTANTE! AÑADE ESTA IMPORTACIÓN ---
import com.example.appapoyoemocional.viewModel.AuthViewModel
// -----------------------------------------
import com.example.appapoyoemocional.viewModel.EmocionViewModel
import com.example.appapoyoemocional.viewModel.InicioViewModel
import com.example.appapoyoemocional.viewModel.PerfilViewModel
import com.example.appapoyoemocional.viewModel.RecFacialViewModel
import com.example.appapoyoemocional.viewModel.RecursosViewModel
import com.example.appapoyoemocional.viewModel.RespiraViewModel
import com.example.appapoyoemocional.viewModel.UsuarioViewModel
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppApoyoEmocionalTheme {
                val postViewModel: com.example.appapoyoemocional.viewModel.PostViewModel = viewModel()
                val navController = rememberNavController()
                val usuarioViewModel: UsuarioViewModel = viewModel()
                val perfilViewModel: PerfilViewModel = viewModel()
                val inicioViewModel: InicioViewModel = viewModel()
                val emocionViewModel: EmocionViewModel = viewModel()
                val respiraViewModel: RespiraViewModel = viewModel()
                val recursosViewModel: RecursosViewModel = viewModel()
                val recFacialViewModel: RecFacialViewModel = viewModel()
                // --- ¡IMPORTANTE! AÑADE LA CREACIÓN DEL VIEWMODEL ---
                val authViewModel: AuthViewModel = viewModel()
                // ---------------------------------------------------

                // MODIFICACIÓN: La ruta inicial ahora es "inicio"
                NavHost(navController = navController, startDestination = "inicio") {

                    composable("inicio") {
                        // En la PaginaInicio, cuando se presiona el botón, se navega a "LoginScreen"
                        PaginaInicio(navController, inicioViewModel)
                    }

                    // --- MODIFICACIÓN 1: AÑADIR LA RUTA PARA LOGINSCREEN ---
                    composable("LoginScreen") {
                        // Pasamos el navController y el authViewModel que acabamos de crear
                        LoginScreen(navController = navController, viewModel = authViewModel)
                    }
                    // -----------------------------------------------------

                    composable("FormularioScreen") {
                        FormularioScreen(navController, usuarioViewModel)
                    }

                    composable("resumen") {
                        val nombreUsuario = usuarioViewModel.estado.collectAsState().value.nombre
                        ResumenScreen(navController, usuarioViewModel, nombreUsuario)
                    }

                    composable("perfil/{nombreUsuario}") { backStackEntry ->
                        val nombreUsuario =
                            backStackEntry.arguments?.getString("nombreUsuario") ?: "Invitado"
                        PerfilScreen(navController, perfilViewModel, nombreUsuario)
                    }

                    // --- MODIFICACIÓN 2: USAR "posts" COMO LA PANTALLA PRINCIPAL ---
                    // En LoginScreen, la navegación exitosa debe apuntar a esta ruta.
                    // Renombraré "posts" a "PaginaPrincipal" para que coincida con el código anterior.
                    composable("PaginaPrincipal") {
                        PostScreen(viewModel = postViewModel, navController = navController)
                    }
                    // --------------------------------------------------------------

                    composable("reconocimiento") {
                        RecFacialScreen(
                            navController = navController,
                            viewModel = recFacialViewModel
                        )
                    }
                    composable("emocion/{nombreUsuario}") { backStackEntry ->
                        val nombre =
                            backStackEntry.arguments?.getString("nombreUsuario") ?: "Invitado"
                        EmocionScreen(navController, emocionViewModel, nombre)
                    }
                    composable("emocionGuardada/{nombreUsuario}/{emocionTexto}") { backStackEntry ->
                        val nombre = backStackEntry.arguments?.getString("nombreUsuario")
                            ?: "Usuario Desconocido"
                        val emocion = backStackEntry.arguments?.getString("emocionTexto")
                            ?: "No se registró emoción."
                        val emocionDecoded =
                            URLDecoder.decode(emocion, StandardCharsets.UTF_8.toString())
                        EmocionGuardadaScreen(navController, nombre, emocionDecoded)
                    }
                    composable("recursos") {
                        RecursosScreen(navController, recursosViewModel)
                    }
                    composable("Respira") {
                        RespiraScreen(navController = navController, viewModel = respiraViewModel)
                    }
                }
            }
        }
    }
}
