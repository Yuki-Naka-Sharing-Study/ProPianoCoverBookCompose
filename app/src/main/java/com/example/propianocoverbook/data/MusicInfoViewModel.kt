package com.example.propianocoverbook.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propianocoverbook.data.MusicInfo
import com.example.propianocoverbook.data.MusicInfoDao
import com.example.propianocoverbook.data.MusicInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MusicInfoViewModel(
    private val repository: MusicInfoRepository,
    private val musicInfoDao: MusicInfoDao
) : ViewModel() {

    // 以下、元々のコード

//    private val _musicInfo = MutableStateFlow<List<MusicInfo>>(emptyList())
//    val musicInfo: StateFlow<List<MusicInfo>> = _musicInfo


    // 以下、参考コード

//    private val _items = MutableStateFlow<List<Item>>(emptyList())
//    val items: StateFlow<List<Item>> get() = _items.asStateFlow()


    // 以下、参考コードをもとに修正したコード
    private val _musicInfo = MutableStateFlow<List<MusicInfo>>(emptyList())
    val musicInfo: StateFlow<List<MusicInfo>> get() = _musicInfo.asStateFlow()
//    fun loadItems() {
//        viewModelScope.launch {
//            _musicInfo.value = repository.getAllItems()
//        }
//    }


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
            // 以下のsaveMusicInfoの引数の箇所に全てブレイクポイントを貼った結果
            // 処理が通っていないことが判明。→ データがそもそも保存されていない。
            // → 宣言元のsaveMusicInfoに原因がある？
            repository.saveMusicInfo(
                textOfMusic,
                textOfArtist,
                textOfGenre,
                textOfStyle,
                textOfMemo,
                numOfRightHand,
                numOfLeftHand,
            )
        }
    }

    // 以下、元々のコード

//    fun insertMusicInfo(musicInfo: MusicInfo) {
//        viewModelScope.launch {
//            musicInfoDao.insertMusicInfo(musicInfo)
//            _musicInfo.value = musicInfoDao.getAllMusicInfo()
//        }
//    }

//    fun deleteMusicInfo(musicInfo: MusicInfo) {
//        viewModelScope.launch {
//            musicInfoDao.deleteMusicInfo(musicInfo)
//            _musicInfo.value = musicInfoDao.getAllMusicInfo()
//        }
//    }
}