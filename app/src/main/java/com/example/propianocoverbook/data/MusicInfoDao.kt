package com.example.propianocoverbook.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.propianocoverbook.data.MusicInfo

@Dao
interface MusicInfoDao {
    @Insert
    suspend fun insertMusicInfo(item: MusicInfo)

    @Delete
    suspend fun deleteMusicInfo(item: MusicInfo)
    @Update
    suspend fun updateMusicInfo(item: MusicInfo)

    @Query("SELECT * FROM music_info")
    suspend fun getAllMusicInfo(): List<MusicInfo>
}