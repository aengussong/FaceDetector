package com.aengussong.facedetector.screen.camera

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.aengussong.facedetector.R
import org.opencv.android.Utils
import org.opencv.core.Mat
import org.opencv.core.MatOfRect
import org.opencv.core.Size
import org.opencv.objdetect.CascadeClassifier
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class OpenCLFaceProcessor : BaseFaceProcessor<Mat>() {
    //Classifier for face detection
    private var cascadeClassifier: CascadeClassifier? = null
    private var grayscaleImage: Mat? = null
    private var absoluteFaceSize = 0.0

    override fun isFacePresent(data: Mat?): List<Mat> {
        val faces = MatOfRect()

        cascadeClassifier?.detectMultiScale(
            grayscaleImage, faces, 1.1, 2, 2,
            Size(absoluteFaceSize, absoluteFaceSize), Size()
        )
        val facesMatrix = mutableListOf<Mat>()
        data?.let {
            faces.toList().take(1).forEach { faceRect ->
                facesMatrix.add(data.submat(faceRect))
            }
        }

        return facesMatrix
    }

    override fun dataToBitmap(data: Mat): Bitmap {
        val bitmap: Bitmap =
            Bitmap.createBitmap(data.width(), data.height(), Bitmap.Config.ARGB_8888)
        Utils.matToBitmap(data, bitmap)
        return bitmap
    }

    fun initOpenCvVariables(grayscaleImage: Mat?, absoluteFaceSize: Double) {
        this.grayscaleImage = grayscaleImage
        this.absoluteFaceSize = absoluteFaceSize
    }

    //init classifier from xml file
    fun initOpenCvDependencies(context: Context) {
        try {
            val iStream: InputStream =
                context.resources.openRawResource(R.raw.haarcascade_frontalface_default)
            val cascadeDir: File = context.getDir("cascade", Context.MODE_PRIVATE)
            val mCascadeFile = File(cascadeDir, "haarcascade_frontalface_default.xml")
            val oStream = FileOutputStream(mCascadeFile)
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (iStream.read(buffer).also { bytesRead = it } != -1) {
                oStream.write(buffer, 0, bytesRead)
            }
            iStream.close()
            oStream.close()
            // Load the cascade classifier
            cascadeClassifier = CascadeClassifier(mCascadeFile.absolutePath)
        } catch (e: Exception) {
            Log.e("OpenCVActivity", "Error loading cascade", e)
        }
    }
}