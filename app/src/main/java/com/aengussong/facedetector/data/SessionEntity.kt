package com.aengussong.facedetector.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SessionEntity(@PrimaryKey val timestamp: String, val facesCount: Int)