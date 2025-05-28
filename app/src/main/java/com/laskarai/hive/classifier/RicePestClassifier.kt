package com.laskarai.hive.classifier

import android.content.Context
import android.graphics.Bitmap
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.common.FileUtil
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer


class RicePestClassifier(context: Context, modelFileName: String = "converted_model.tflite") {

    private var interpreter: Interpreter
    private val inputImageWidth = 250
    private val inputImageHeight = 250

    private val labels = listOf(
        "asiatic_rice_borer",
        "brown_plant_hopper",
        "paddy_stem_maggot",
        "rice_gall_midge",
        "rice_leaf_caterpillar",
        "rice_leaf_hopper",
        "rice_leaf_roller",
        "rice_shell_pest",
        "rice_stem_fly",
        "rice_water_weevil",
        "yellow_rice_borer",
    )

    init {
        val modelBuffer = loadModelFile(context, modelFileName)

        val options = Interpreter.Options()
        // options.setNumThreads(4)

        interpreter = Interpreter(modelBuffer, options)
    }

    private fun loadModelFile(context: Context, modelFileName: String): MappedByteBuffer {
        return FileUtil.loadMappedFile(context, modelFileName)

    }

    fun classify(bitmap: Bitmap): Pair<String, FloatArray> {
        val resizedBitmap = Bitmap.createScaledBitmap(
            bitmap,
            inputImageWidth,
            inputImageHeight,
            true // filter = true for bilinear filtering
        )

//        val tensorImage = TensorImage(DataType.FLOAT32)
//        tensorImage.load(resizedBitmap)
//
//        val inputBuffer = tensorImage.buffer

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(resizedBitmap)

        val inputBuffer = tensorImage.buffer.rewind()

        // Normalisasi buffer (buat ByteBuffer jadi float dan dibagi 255)
        val floatBuffer = ByteBuffer.allocateDirect(4 * 250 * 250 * 3)
        floatBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(250 * 250)
        resizedBitmap.getPixels(pixels, 0, 250, 0, 0, 250, 250)

        for (pixel in pixels) {
            val r = ((pixel shr 16) and 0xFF) / 255.0f
            val g = ((pixel shr 8) and 0xFF) / 255.0f
            val b = (pixel and 0xFF) / 255.0f

            floatBuffer.putFloat(r)
            floatBuffer.putFloat(g)
            floatBuffer.putFloat(b)
        }
        floatBuffer.rewind()

        val outputBuffer = TensorBuffer.createFixedSize(intArrayOf(1, labels.size), DataType.FLOAT32)

        interpreter.run(floatBuffer, outputBuffer.buffer.rewind())

        val probabilities = outputBuffer.floatArray
        val predictedIndex = probabilities.indices.maxByOrNull { probabilities[it] } ?: -1

        val predictedLabel = if (predictedIndex != -1) labels[predictedIndex] else "Unknown"
        return Pair(predictedLabel, probabilities)
    }

    fun close() {
        interpreter.close()
    }
}