package com.aengussong.facedetector.data

import android.graphics.Bitmap

interface FaceRepository{

    /**
     * Stores detected faces and whole camera frame
     *
     * @param photo - [Bitmap] representation of camera frame
     * @param croppedFaces - list of cropped faces
     * @returns id of saved session
     * */
    fun saveData(photo: Bitmap, croppedFaces: List<Bitmap>): String
}