package com.aengussong.facedetector.data

import android.graphics.Bitmap
import io.reactivex.Single

interface FaceRepository {

    /**
     * Stores detected faces and whole camera frame
     *
     * @param photo - [Bitmap] representation of camera frame
     * @param croppedFaces - list of cropped faces
     * @returns id of saved session
     * */
    fun saveData(photo: Bitmap, croppedFaces: List<Bitmap>): String

    /**
     * Get list of faces for session with [timestamp] id. First item in the list should be the whole
     * image, unless error on file reading occurred, and the rest are cropped faces
     *
     * @param timestamp - unique identifier of the session
     * @return returns all images, associated with this session
     * */
    fun getFaces(timestamp: String): Single<List<Bitmap>>
}