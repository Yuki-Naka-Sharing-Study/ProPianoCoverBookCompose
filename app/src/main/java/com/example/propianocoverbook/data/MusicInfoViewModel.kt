package com.example.propianocoverbook.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propianocoverbook.data.MusicInfo
import com.example.propianocoverbook.data.MusicInfoDao
import com.example.propianocoverbook.data.MusicInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MusicInfoViewModel(
    private val repository: MusicInfoRepository,
    private val musicInfoDao: MusicInfoDao
) : ViewModel() {

    private val _musicInfo = MutableStateFlow<List<MusicInfo>>(emptyList())
    val musicInfo: StateFlow<List<MusicInfo>> = _musicInfo

    init {
        viewModelScope.launch {
            _musicInfo.value = musicInfoDao.getAllMusicInfo()
        }
    }

    fun saveValues(textOfMusic: String, textOfArtist: String, textOfGenre: String, textOfStyle: String, textOfMemo: String) {
        viewModelScope.launch {
            repository.saveMusicInfo(
                textOfMusic,
                textOfArtist,
                textOfGenre,
                textOfStyle,
                textOfMemo)
        }
    }

    fun insertMusicInfo(musicInfo: MusicInfo) {
        viewModelScope.launch {
            musicInfoDao.insertMusicInfo(musicInfo)
            _musicInfo.value = musicInfoDao.getAllMusicInfo()
        }
    }

    fun deleteMusicInfo(musicInfo: MusicInfo) {
        viewModelScope.launch {
            musicInfoDao.deleteMusicInfo(musicInfo)
            _musicInfo.value = musicInfoDao.getAllMusicInfo()
        }
    }
}