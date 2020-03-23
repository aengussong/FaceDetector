package com.aengussong.facedetector.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single

@Dao
interface SessionDao{

    @Query("SELECT * FROM sessionentity WHERE timestamp=:timestamp")
    fun getByTimeStamp(timestamp:String): Single<SessionEntity>

    @Query("SELECT * FROM sessionentity ORDER BY timestamp DESC")
    fun getPagedSessions(): DataSource.Factory<Int, SessionEntity>

    @Insert
    fun insert(entity:SessionEntity)
}