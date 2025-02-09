package com.example.propianocoverbook.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.propianocoverbook.di.MusicDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicInfoViewModel @Inject constructor(
    private val repository: MusicInfoRepository,
    private val musicInfoDao: MusicInfoDao,
    @MusicDataStore private val dataStore: DataStore<Preferences>
) : ViewModel() {
    private val _musicInfo = MutableStateFlow<List<MusicInfo>>(emptyList())
    val musicInfo: StateFlow<List<MusicInfo>> get() = _musicInfo.asStateFlow()

    private val ONBOARDING_COMPLETED_KEY = booleanPreferencesKey("onboarding_completed")

    val isFirstLaunch: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[ONBOARDING_COMPLETED_KEY] ?: true
    }

    fun completeOnboarding() {
        viewModelScope.launch {
            dataStore.edit { preferences ->
                preferences[ONBOARDING_COMPLETED_KEY] = false
            }
        }
    }

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