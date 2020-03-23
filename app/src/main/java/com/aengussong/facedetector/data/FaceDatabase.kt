package com.aengussong.facedetector.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.aengussong.facedetector.app.FaceDetectorApp

const val DATABASE_NAME = "face_database"

@Database(entities = [SessionEntity::class], version = 1, exportSchema = false)
abstract class FaceDatabase : RoomDatabase() {
    abstract fun sessionDao(): SessionDao

    companion object {
        private var instance: FaceDatabase? = null

        fun getInstance(): FaceDatabase {
            return instance ?: buildDatabase().also { instance = it }
        }

        private fun buildDatabase(): FaceDatabase {
            return Room.databaseBuilder(
                FaceDetectorApp.appContext,
                FaceDatabase::class.java,
                DATABASE_NAME
            ).build()
        }
    }
}