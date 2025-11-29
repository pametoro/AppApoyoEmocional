package com.example.appapoyoemocional.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.appapoyoemocional.viewModel.AuthViewModel

val fondoPastel = Color(0xFFE3F2FD) // Azul cielo pastel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    // Estados locales para los campos de texto
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    // --- MODIFICACIÓN 1: Observar el estado del ViewModel ---
    val loginExitoso by viewModel.loginExitoso.collectAsState()

    // --- MODIFICACIÓN 2: Reaccionar al estado con LaunchedEffect ---
    // LaunchedEffect ejecuta el bloque de código cuando 'loginExitoso' cambia a 'true'.
    // Es la forma correcta de llamar a funciones de navegación desde un Composable.
    LaunchedEffect(loginExitoso) {
        if (loginExitoso) {
            // Navega a la pantalla principal y limpia la pila de navegación
            // para que el usuario no pueda volver a Login con el botón "Atrás".
            navController.navigate("PaginaPrincipal") { // Asegúrate que "PaginaPrincipal" sea la ruta correcta
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
                }
            }
            // Resetea el estado en el ViewModel para futuras interacciones.
            viewModel.resetLoginState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoPastel)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "INICIO DE SESIÓN",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = correo,
            onValueChange = { correo = it },
            label = { Text("Correo Electrónico") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = clave,
            onValueChange = { clave = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                // --- MODIFICACIÓN 3: La única responsabilidad del botón es iniciar la acción ---
                // Ya no intenta navegar directamente.
                viewModel.iniciarSesion(correo, clave)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("FormularioScreen") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}