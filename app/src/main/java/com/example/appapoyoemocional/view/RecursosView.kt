package com.example.appapoyoemocional.view

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { navController.navigate("Respira") }) {
                    Text("Ver ejercicio de respiración")
                }
            }
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

                val infiniteTransition = rememberInfiniteTransition()

                val size by infiniteTransition.animateFloat(
                    initialValue = 100f,
                    targetValue = 200f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 4000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    )
                )

                val textPhase by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 8000, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
                )

                val phaseText = if (textPhase < 0.5f) "Inhala..." else "Exhala..."

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFE3F2FD)), // Fondo azul pastel
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(size.dp)
                            .background(Color(0xFF80DEEA), CircleShape) // Círculo turquesa
                    )

                    Text(
                        text = phaseText,
                        fontSize = 28.sp,
                        color = Color(0xFF006064),
                        modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp)
                    )
                }
                Spacer(modifier = Modifier.height(50.dp))

            }
        }
    }
}
