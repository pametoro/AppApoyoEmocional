package com.example.appapoyoemocional.data.modelo

import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetectorOptions
object RecFacialModel {
    private val options = FaceDetectorOptions.Builder()
        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
        .build()

    private val detector = FaceDetection.getClient(options)

    fun detectFaces(image: InputImage, onResult: (List<Face>) -> Unit, onError: (Exception) -> Unit) {
        detector.process(image)
            .addOnSuccessListener { faces -> onResult(faces) }
            .addOnFailureListener { e -> onError(e) }
    }
}