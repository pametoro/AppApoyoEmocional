package com.example.appapoyoemocional.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appapoyoemocional.viewModel.RecursosViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun RecursosScreen(navController: NavController, viewModel: RecursosViewModel) {
    val listaConsejos = viewModel.consejos.collectAsState()
    val fondoPastel = Color(0xFFE3F2FD) // Azul cielo pastel

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Consejos para Sentirte Mejor",
                        color = Color(0xFF03A9F4)
                    )
                },
                navigationIcon = {
                    Button(onClick = { navController.popBackStack() }) {
                        Text("<")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoPastel)
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(listaConsejos.value.size) { index ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFB2DFDB) // Verde pastel
                    )
                ) {
                    Text(
                        text = listaConsejos.value[index].texto,
                        modifier = Modifier.padding(20.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = "Si en este momento sientes que la angustia te supera, regálate un respiro mira el video y acompáñate en calma.",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp)
                )

                Spacer(modifier = Modifier.height(50.dp))

                Button(onClick = {
                    navController.navigate("Respira")
                }) {
                    Text("Ver ejercicio de respiración")
                }
            }
        }
    }
}
