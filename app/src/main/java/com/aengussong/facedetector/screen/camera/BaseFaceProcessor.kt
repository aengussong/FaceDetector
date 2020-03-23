package com.aengussong.facedetector.screen.camera

import android.graphics.Bitmap

/**
 * Base class for processing data from camera for face detection. Every face processor implementation
 * should extend this class.
 *
 * @param T - object representation of camera data
 * */
abstract class BaseFaceProcessor<T : Any> {

    /**
     * Detects faces from camera data
     *
     * @param data - data from camera
     * @return list of cropped faces representation. If no face was detected - returns empty list
     * */
    abstract fun isFacePresent(data: T?): List<T>

    /**
     * Converts data from camera to [Bitmap]
     *
     * @param data - data from camera
     * @return [Bitmap] representation of camera data
     * */
    abstract fun dataToBitmap(data: T): Bitmap

    /**
     * Main function to be called for face detection
     *
     * @param data - data from camera
     * @param callback - callback, that will be called if some faces were detected on provided camera data,
     * provides [Bitmap] representation of camera data  and list of cropped faces in [Bitmap] representation
     * */
    open fun processData(data: T, callback: (wholePhoto:Bitmap, croppedFaces:List<Bitmap>) -> Unit) {
        val faces = isFacePresent(data)
        if (faces.isNotEmpty()) {
            val wholePhoto = dataToBitmap(data)
            val croppedFaces = dataToBitmap(faces)

            callback(wholePhoto, croppedFaces)
        }
    }

    /**
     * Converts list of camera frames to [Bitmap]
     *
     * @param data - list of camera frames (data, provided from camera)
     * @return list of camera frames, coverted to [Bitmap]
     * */
    open fun dataToBitmap(data: List<T>): List<Bitmap> {
        val bitmaps = mutableListOf<Bitmap>()
        data.forEach {
            bitmaps.add(dataToBitmap(it))
        }
        return bitmaps
    }
}