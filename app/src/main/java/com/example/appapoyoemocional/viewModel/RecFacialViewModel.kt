package com.example.appapoyoemocional.viewModel

import android.graphics.Bitmap
import com.google.mlkit.vision.face.Face
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appapoyoemocional.data.modelo.RecFacialModel
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RecFacialViewModel  : ViewModel() {
    private val _faces = MutableStateFlow<List<Face>>(emptyList())
    val faces: StateFlow<List<Face>> = _faces

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun processImage(image: InputImage) {
        RecFacialModel.detectFaces(
            image,
            onResult = { detectedFaces -> _faces.value = detectedFaces },
            onError = { e -> _error.value = e.message }
        )
    }

    private val _emocion = MutableStateFlow("Desconocida")
    val emocion: StateFlow<String> = _emocion

    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
        .build()

    private val detector = FaceDetection.getClient(options)

    fun procesarImagen(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)

        viewModelScope.launch {
            detector.process(image)
                .addOnSuccessListener { faces ->
                    if (faces.isNotEmpty()) {
                        val face = faces[0]
                        val smilingProb = face.smilingProbability ?: -1f

                        _emocion.value = if (smilingProb > 0.7f) {
                            "Feliz"
                        } else {
                            "Serio"
                        }
                    } else {
                        _emocion.value = "No detectado"
                    }
                }
                .addOnFailureListener {
                    _emocion.value = "Error"
                }
        }
    }
}
