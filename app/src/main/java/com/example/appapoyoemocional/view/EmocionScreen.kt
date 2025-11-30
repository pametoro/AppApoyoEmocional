package com.example.appapoyoemocional.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appapoyoemocional.viewModel.EmocionViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun EmocionScreen(navController: NavController, viewModel: EmocionViewModel, nombre: String) {
    // CORRECCIÓN CLAVE: Usamos el argumento 'nombre' para inicializar el ViewModel
    LaunchedEffect(nombre) {
        // Actualizamos el nombre de usuario en el ViewModel con el argumento recibido de la navegación.
        viewModel.actualizarNombre(nombre)
    }

    val estado by viewModel.estado.collectAsState()
    val fondoPastel = Color(0xFFE3F2FD) // Azul cielo pastel
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {SnackbarHost(snackbarHostState)},
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = estado.titulo,
                            fontSize = 30.sp ,
                            style = MaterialTheme.typography.titleLarge,
                            color = Color(0xFF03A9F4)
                        )
                    }
                },
                navigationIcon = {
                    Button(onClick = { navController.popBackStack() }) {
                        Text("<")
                    }
                }
            )
        }

    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoPastel)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = estado.descripcion,
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(26.dp))

                // Muestra "Hola, [Nombre del Usuario]"
                Text(
                    text = "Hola, ${estado.nombreUsuario}",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "¿Cómo te sientes hoy?",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 16.sp

                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = estado.emocionTexto,
                    onValueChange = { viewModel.actualizarEmocion(it) },
                    label = { Text("Escribe tus emociones") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 18.sp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val isSaved = viewModel.guardarEmocion()
                        if (isSaved) {
                            val nombreEncoded = URLEncoder.encode(
                                estado.nombreUsuario,
                                StandardCharsets.UTF_8.toString()
                            )
                            val emocionEncoded = URLEncoder.encode(
                                estado.emocionTexto,
                                StandardCharsets.UTF_8.toString()
                            )

                            navController.navigate("emocionGuardada/$nombreEncoded/$emocionEncoded")
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("Por favor, escribe tus emociones antes de guardar.")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ){
                    Text("Guardar")
                }
                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { navController.navigate("recursos") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Consejos")
                }
            }
        }
    }
}

