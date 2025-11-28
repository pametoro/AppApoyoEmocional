package com.example.appapoyoemocional.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appapoyoemocional.viewModel.UsuarioViewModel


@Composable
fun ResumenScreen(navController: NavController, viewModel: UsuarioViewModel, nombreUsuario: String) {
    val estado by viewModel.estado.collectAsState()
    val fondoPastel = Color(0xFFE3F2FD) // Azul cielo pastel

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoPastel)
            .padding(16.dp)
    ) {

        Text("Datos ingresados con exito", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Nombre: ${estado.nombre}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Correo: ${estado.correo}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Dirección: ${estado.direccion}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Contraseña: ${"*".repeat(estado.clave.length)}")
        Spacer(modifier = Modifier.height(8.dp))
        Text("¿Términos Aceptados?: ${if (estado.aceptaTerminos) "Aceptados" else "Declinados"}")

        Spacer(Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp) // espacio entre botones
        ) {
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text("Volver")
            }

            Button(
                onClick = { navController.navigate("posts") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red,
                    contentColor = Color.White
                )
            ) {
                Text("Ver Posts")
            }

            Button(
                onClick = {
                    val nombreSeguro = "Invitado" // o el nombre real del usuario
                    navController.navigate("perfil/$nombreSeguro")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text("Ir al Perfil")
            }
        }
    }
}