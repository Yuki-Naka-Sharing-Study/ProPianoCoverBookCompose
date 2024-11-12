package com.example.propianocoverbook

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MusicInfoDao {
    @Insert
    suspend fun insertMusicInfo(item: MusicInfo)

    @Delete
    suspend fun deleteMusicInfo(item: MusicInfo)

    @Query("SELECT * FROM music_info")
    suspend fun getAllMusicInfo(): List<MusicInfo>
}