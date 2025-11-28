package com.example.appapoyoemocional.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmocionGuardadaScreen(navController: NavController, nombreUsuario: String, emocionTexto: String) {
    val fondoPastel = Color(0xFFE3F2FD) // Azul cielo pastel
    val colorPrincipal = Color(0xFF03A9F4)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Emoción Registrada",
                        style = MaterialTheme.typography.titleLarge,
                        color = colorPrincipal
                    )
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoPastel)
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título de éxito
            Text(
                text = "¡Emoción guardada con éxito!",
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineSmall,
                color = colorPrincipal
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Nombre del usuario
            Text(
                text = "Hola, ${nombreUsuario}",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Contenido de la Emoción
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Usuario dijo:",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = emocionTexto,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Botón para volver
            Button(
                onClick = { navController.popBackStack() }, // Simplemente vuelve a la pantalla anterior
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Volver a la reflexión")
            }
        }
    }
}