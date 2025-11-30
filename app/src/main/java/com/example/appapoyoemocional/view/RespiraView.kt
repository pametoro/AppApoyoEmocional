package com.example.appapoyoemocional.view

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.appapoyoemocional.R
import com.example.appapoyoemocional.view.components.VideoPlayer
import com.example.appapoyoemocional.viewModel.RespiraViewModel



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RespiraScreen(navController: NavController, viewModel: RespiraViewModel) {
    val estado by viewModel.estado
    val fondoPastel = Color(0xFFE3F2FD)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = estado.titulo,
                        style = MaterialTheme.typography.headlineSmall,
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
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(fondoPastel)
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.descarga_yoga),
                contentDescription = "persona haciendo yoga",
                modifier = Modifier
                    .size(280.dp)
                    .padding(bottom = 25.dp))

            Text(
                text = "Respira profundamente y sigue las instrucciones del video",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 25.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(24.dp))

            val videoUri = Uri.parse(estado.url) // estado.url es String, ahora videoUri es Uri (CORRECTO)

            VideoPlayer(
                videoUri = videoUri,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                navController.navigate("inicio") {
                    popUpTo(0) // Limpia el backstack para evitar volver atrás
                }
            }) {
                Text("Cerrar sesión")
            }
        }

    }
}