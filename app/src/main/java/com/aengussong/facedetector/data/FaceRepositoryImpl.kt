package com.aengussong.facedetector.data

import android.graphics.Bitmap
import java.util.*

class FaceRepositoryImpl:FaceRepository{
    override fun saveData(photo: Bitmap, croppedFaces: List<Bitmap>): String {
//        todo proper implementation
        return Date().toString()
    }

}