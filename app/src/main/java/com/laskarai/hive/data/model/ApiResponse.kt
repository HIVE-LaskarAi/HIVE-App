package com.laskarai.hive.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PredictionResponse(
    @SerialName("confidence")
    val confidence: Float,
    @SerialName("predicted_class")
    val predictedClass: String
)