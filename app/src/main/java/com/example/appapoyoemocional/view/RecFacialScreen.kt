package com.example.appapoyoemocional.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.appapoyoemocional.viewModel.RecFacialViewModel

@Composable
fun RecFacialScreen(navController: NavController, viewModel: RecFacialViewModel) {
    val emocion by viewModel.emocion.collectAsState()
    val context = LocalContext.current
    val fondoPastel = Color(0xFFE3F2FD)

    // Estado de permisos
    var hasCameraPermission by remember {
        mutableStateOf(checkCameraPermission(context))
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        hasCameraPermission = isGranted
    }

    // Solicitud inicial del permiso
    LaunchedEffect(Unit) {
        if (!hasCameraPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(fondoPastel)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Botón de retroceso
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Text("<")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Análisis Emocional en Vivo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF006064),
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        // Cámara o mensaje de permiso
        if (hasCameraPermission) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .aspectRatio(1f)
                    .padding(8.dp)
            ) {
                // LLAMADA AL COMPONENTE DE CÁMARA CORREGIDO
                CameraFaceDetector(
                    onEmotionDetected = { emotion -> viewModel.updateDetectedEmotion(emotion) }
                )
            }
        } else {
            Spacer(modifier = Modifier.height(64.dp))
            Text(
                "La cámara es necesaria para el reconocimiento facial.",
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(32.dp)
            )
            Button(onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }) {
                Text("Habilitar Cámara")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Resultado de emoción
        Text(
            text = "Emoción detectada: $emocion",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF006064)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Navegación según emoción
        Button(onClick = {
            when (emocion) {
                "Feliz" -> navController.navigate("recursos")
                "Serio" -> navController.navigate("Respira")
                else -> navController.navigate("recursos")
            }
        }) {
            Text("Continuar")
        }

        Button(
            onClick = { navController.navigate("perfil/Invitado") },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Omitir")
        }
    }
}

private fun checkCameraPermission(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}
