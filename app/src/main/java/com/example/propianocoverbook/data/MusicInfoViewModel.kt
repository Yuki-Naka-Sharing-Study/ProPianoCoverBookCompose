package com.example.propianocoverbook.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MusicInfoViewModel(
    private val repository: MusicInfoRepository,
    private val musicInfoDao: MusicInfoDao
) : ViewModel() {
    private val _musicInfo = MutableStateFlow<List<MusicInfo>>(emptyList())
    val musicInfo: StateFlow<List<MusicInfo>> get() = _musicInfo.asStateFlow()

    init {
        viewModelScope.launch {
            _musicInfo.value = musicInfoDao.getAllMusicInfo()
        }
    }
    fun loadAllMusicInfo() {
        viewModelScope.launch {
            _musicInfo.value = repository.getAllMusicInfo()
        }
    }
    fun saveValues(
        textOfMusic: String,
        textOfArtist: String,
        textOfGenre: String,
        textOfStyle: String,
        textOfMemo: String,
        numOfRightHand: Float,
        numOfLeftHand: Float,
        )
    {
        viewModelScope.launch {
            repository.saveMusicInfo(
                textOfMusic,
                textOfArtist,
                textOfGenre,
                textOfStyle,
                textOfMemo,
                numOfRightHand,
                numOfLeftHand,
            )
            loadAllMusicInfo()
        }
    }
    fun deleteMusicValues(musicInfo: MusicInfo) {
        viewModelScope.launch {
            repository.deleteMusicInfo(musicInfo)
            this@MusicInfoViewModel._musicInfo.value = musicInfoDao.getAllMusicInfo()
        }
    }
    fun updateMusicValues(musicInfo: MusicInfo) {
        viewModelScope.launch {
            repository.updateMusicInfo(musicInfo)
            this@MusicInfoViewModel._musicInfo.value = musicInfoDao.getAllMusicInfo()
        }
    }
}