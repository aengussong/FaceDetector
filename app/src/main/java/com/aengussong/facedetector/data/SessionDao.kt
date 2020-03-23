package com.aengussong.facedetector.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface SessionDao{

    @Query("SELECT * FROM sessionentity WHERE timestamp=:timestamp")
    fun getByTimeStamp(timestamp:String): Single<SessionEntity>

    @Insert
    fun insert(entity:SessionEntity)
}