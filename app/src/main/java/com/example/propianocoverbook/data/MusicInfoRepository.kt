package com.example.propianocoverbook.data

class MusicInfoRepository(private val musicInfoDao: MusicInfoDao) {
    suspend fun getAllItems() = musicInfoDao.getAllMusicInfo()

    suspend fun saveMusicInfo(
        textOfMusic: String,
        textOfArtist: String,
        textOfGenre: String,
        textOfStyle: String,
        textOfMemo: String,
        numOfRightHand: Float,
        numOfLeftHand: Float
    ){
        val musicInfo = MusicInfo(
            nameOfMusic = textOfMusic,
            nameOfArtist = textOfArtist,
            nameOfJenre = textOfGenre,
            nameOfStyle = textOfStyle,
            nameOfMemo = textOfMemo,
            levelOfRightHand = numOfRightHand.toInt(),
            levelOfLeftHand = numOfLeftHand.toInt()
        )

        musicInfoDao.insertMusicInfo(musicInfo)

    }
}