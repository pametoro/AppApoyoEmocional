package com.example.appapoyoemocional.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appapoyoemocional.R
import com.example.appapoyoemocional.viewModel.InicioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaginaInicio(navController: NavController, viewModel: InicioViewModel) {
    val estado by viewModel.estado.collectAsState()
    val fondoPastel = Color(0xFFE3F2FD) // Azul cielo pastel


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(estado.titulo)
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
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_apoyo_emocional),
                        contentDescription = "Logo de Apoyo Emocional",
                        modifier = Modifier
                            .size(220.dp)
                            .padding(bottom = 16.dp)
                    )
                }

                Text(
                    text = estado.descripcion,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 25.sp,
                    color = Color(0xFF006064)
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (estado.mostrarBoton) {
                    Button(onClick = {
                        navController.navigate("FormularioScreen")
                    }) {
                        Text("Registrate")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            val nombreSeguro = "Invitado" // o algún valor fijo
                            navController.navigate("perfil/$nombreSeguro")
                        }
                    ) {
                        Text("Ir al Perfil")
                    }
                    Button(onClick = { navController.navigate("reconocimiento") }) {
                        Text("Iniciar reconocimiento facial")

                    }
                }
            }
        }
    }
}

