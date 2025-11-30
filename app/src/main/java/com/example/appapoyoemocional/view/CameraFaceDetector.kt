package com.example.appapoyoemocional.view

import android.annotation.SuppressLint
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.example.appapoyoemocional.data.modelo.RecFacialModel
import com.google.mlkit.vision.face.Face
import java.util.concurrent.Executors

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun CameraFaceDetector(onEmotionDetected: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Executor de un solo hilo para el análisis de imagen (evita bloquear la UI)
    val analysisExecutor = remember { Executors.newSingleThreadExecutor() }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder().build().apply {
                    setSurfaceProvider(previewView.surfaceProvider)
                }

                val analyzer = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()
                    .also {
                        // Usar el Executor de fondo para el análisis
                        it.setAnalyzer(analysisExecutor) { imageProxy ->
                            RecFacialModel.detectFaces(
                                imageProxy,
                                onResult = { faces ->
                                    if (faces.isNotEmpty()) {
                                        val emocion = interpretarEmocion(faces.first())
                                        onEmotionDetected(emocion)
                                    }
                                },
                                onError = {
                                    // Manejo de error si es necesario (p.ej., log)
                                }
                            )
                        }
                    }

                val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

                try {
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        lifecycleOwner,
                        cameraSelector,
                        preview,
                        analyzer
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, ContextCompat.getMainExecutor(ctx))

            previewView
        },
        modifier = Modifier.fillMaxSize()
    )

    // Detiene el executor cuando el Composable deja de ser visible
    DisposableEffect(Unit) {
        onDispose {
            analysisExecutor.shutdown()
        }
    }
}

/**
 * Interpreta la probabilidad de sonrisa de ML Kit para devolver una emoción simple.
 */
private fun interpretarEmocion(face: Face): String {
    val sonrisa = face.smilingProbability ?: -1f
    return when {
        // Asumiendo > 70% de probabilidad de sonrisa es Feliz
        sonrisa > 0.7f -> "Feliz"
        // Entre 30% y 70% es Neutral
        sonrisa in 0.3f..0.7f -> "Neutral"
        // Baja probabilidad o ninguna es Serio
        sonrisa >= 0f -> "Serio"
        else -> "Desconocido"
    }
}