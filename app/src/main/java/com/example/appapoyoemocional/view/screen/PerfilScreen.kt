package com.example.appapoyoemocional.view.screen

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.navigation.NavController
import com.example.appapoyoemocional.viewModel.PerfilViewModel
import com.example.appapoyoemocional.view.components.ImagenInteligente
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun PerfilScreen(navController: NavController, viewModel: PerfilViewModel, nombreUsuario: String){
    val context = LocalContext.current
    val imagenUri by viewModel.imagenUri.collectAsState()
    var cameraUri by remember { mutableStateOf<Uri?>(null) }

    // Función para crear un URI seguro para la cámara
    fun createImageUri(context: Context): Uri {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val file = File.createTempFile("JPEG_${timestamp}_", ".jpg", storageDir)
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )
    }

    // Launcher para tomar la foto
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) viewModel.setImage(cameraUri)
    }

    // Launcher para pedir el permiso de la cámara
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Si el permiso se concede, lanzamos la cámara
            val uri = createImageUri(context)
            cameraUri = uri
            takePictureLauncher.launch(uri)
        } else {
            // Opcional: Mostrar un mensaje si el permiso es denegado
        }
    }

    // Launcher para seleccionar imagen de la galería
    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        viewModel.setImage(uri)
    }

    val fondoPastel = Color(0xFFE3F2FD) // Azul cielo pastel

    Box(
        modifier = Modifier
            .background(fondoPastel)
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            ImagenInteligente(imagenUri)

            Spacer(modifier = Modifier.height(24.dp))

            Button(onClick = { pickImageLauncher.launch("image/*") }) {
                Text("Selecciona tu imagen desde galería")
            }
            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                when (PackageManager.PERMISSION_GRANTED) {
                    ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) -> {
                        // Si ya tenemos permiso, lanzamos la cámara
                        val uri = createImageUri(context)
                        cameraUri = uri
                        takePictureLauncher.launch(uri)
                    }
                    else -> {
                        // Si no tenemos permiso, lo pedimos
                        permissionLauncher.launch(Manifest.permission.CAMERA)
                    }
                }
            }) {
                Text("Toma una foto con la cámara")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                navController.navigate("emocion/$nombreUsuario")
            }) {
                Text("Omitir")
            }
        }
    }
}
