package com.aengussong.facedetector.data

import android.content.Context.MODE_PRIVATE
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.paging.PagedList
import androidx.paging.toObservable
import com.aengussong.facedetector.app.FaceDetectorApp
import io.reactivex.Observable
import io.reactivex.Single
import java.io.*
import java.text.SimpleDateFormat
import java.util.*

const val FACES_DIRECTORY = "faces"
const val IMAGE_EXTENTION = ".jpg"
const val PREFIX_DELIMITER = "_"
const val FACE_PREFIX = "face"

const val PAGE_SIZE = 10

class FaceRepositoryImpl : FaceRepository {

    private val db: FaceDatabase = FaceDatabase.getInstance()
    private val sdf = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())

    override fun saveData(photo: Bitmap, croppedFaces: List<Bitmap>): String {
        val timestamp = getTimestamp()

        saveToInternalStorage(photo, timestamp)
        croppedFaces.forEachIndexed { index, face ->
            saveToInternalStorage(face, timestamp, index)
        }

        saveToDb(timestamp, croppedFaces.count())
        return timestamp
    }

    override fun getFaces(timestamp: String): Single<List<Bitmap>> {
        return db.sessionDao().getByTimeStamp(timestamp).flatMap { session ->
            val sessionTimestamp = session.timestamp
            val bitmaps = mutableListOf<Bitmap>()
            getFromInternalStorage(sessionTimestamp)?.let { bitmaps.add(it) }
            for (i in 0..session.facesCount) {
                getFromInternalStorage(sessionTimestamp, i)?.let { bitmaps.add(it) }
            }
            Single.just(bitmaps)
        }
    }

    override fun getPagedSessions(): Observable<PagedList<SessionEntity>> {
        return db.sessionDao().getPagedSessions().toObservable(pageSize = PAGE_SIZE)
    }

    private fun getTimestamp(): String = sdf.format(Date())

    private fun getImagePath(timestamp: String, facePrefix: Int): File {
        val cw = ContextWrapper(FaceDetectorApp.appContext)
        val directory: File = cw.getDir(FACES_DIRECTORY, MODE_PRIVATE)
        return File(directory, formFileName(timestamp, facePrefix))
    }

    private fun saveToInternalStorage(
        bitmapImage: Bitmap,
        timestamp: String,
        facePrefix: Int = -1
    ) {
        val imagePath = getImagePath(timestamp, facePrefix)
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(imagePath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun getFromInternalStorage(
        timestamp: String,
        facePrefix: Int = -1
    ): Bitmap? {
        var bitmap: Bitmap? = null
        try {
            val cw = ContextWrapper(FaceDetectorApp.appContext)
            val directory: File = cw.getDir(FACES_DIRECTORY, MODE_PRIVATE)
            val imagePath = getImagePath(timestamp, facePrefix)

            bitmap = BitmapFactory.decodeStream(FileInputStream(imagePath))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }

        return bitmap
    }

    private fun saveToDb(timestamp: String, facesCount: Int) {
        db.sessionDao().insert(SessionEntity(timestamp, facesCount))
    }

    /**
     * Forms file name depending on face prefix. Image with [facePrefix] -1 considered to be uncropped
     * and saved without face prefix, e.g. 201604032323.jpg. Image with different face prefix considered
     * to be cropped image of face, and saved with prefix, e.g. 201604032323_face_1.jpg
     *
     * @param timestamp - name of the file
     * @param facePrefix - prefix for the file name, if prefix is -1, it will not be added to the name
     * */
    private fun formFileName(timestamp: String, facePrefix: Int): String {
        val builder = StringBuilder(timestamp)

        if (facePrefix != -1) {
            builder.append(PREFIX_DELIMITER)
                .append(FACE_PREFIX)
                .append(PREFIX_DELIMITER)
                .append(facePrefix)
        }

        return builder.append(IMAGE_EXTENTION)
            .toString()
    }

}