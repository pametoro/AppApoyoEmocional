package com.example.appapoyoemocional.data.modelo

import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions

object RecFacialModel {

    // Configuración del detector:
    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
        // Es esencial usar CLASSIFICATION_MODE_ALL para obtener la probabilidad de sonrisa.
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_NONE)
        .build()

    private val detector = FaceDetection.getClient(options)

    /**
     * Procesa el ImageProxy de CameraX para detectar caras usando ML Kit.
     * Cierra el ImageProxy después de que la tarea asíncrona finaliza.
     */
    fun detectFaces(
        imageProxy: ImageProxy,
        onResult: (List<Face>) -> Unit,
        onError: () -> Unit
    ) {
        @androidx.annotation.OptIn(markerClass = [androidx.camera.core.ExperimentalGetImage::class])
        val mediaImage = imageProxy.image

        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            detector.process(image)
                .addOnSuccessListener { faces ->
                    onResult(faces)
                    imageProxy.close() // Cierra el proxy en el hilo principal de ML Kit (éxito)
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                    onError()
                    imageProxy.close() // Cierra el proxy en caso de error
                }
        } else {
            imageProxy.close() // Cierra si mediaImage es nulo
        }
    }
}