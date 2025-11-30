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
// --- CORRECCIÓN 1: Cambiar el nombre en la importación ---
import com.example.appapoyoemocional.view.RecursosView
// ----------------------------------------------------
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
                val authViewModel: AuthViewModel = viewModel()

                NavHost(navController = navController, startDestination = "inicio") {

                    composable("inicio") {
                        PaginaInicio(navController, inicioViewModel)
                    }

                    composable("LoginScreen") {
                        LoginScreen(navController = navController, viewModel = authViewModel)
                    }

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

                    composable("PaginaPrincipal") {
                        PostScreen(viewModel = postViewModel, navController = navController)
                    }

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
                        // --- CORRECCIÓN 2: Usar el nombre correcto de la función ---
                        RecursosView(viewModel = recursosViewModel)
                        // --------------------------------------------------------
                    }
                    composable("Respira") {
                        RespiraScreen(navController = navController, viewModel = respiraViewModel)
                    }
                }
            }
        }
    }
}
