package com.example.propianocoverbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MusicInfoViewModelFactory(
    private val repository: MusicInfoRepository,
    private val musicInfoDao: MusicInfoDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MusicInfoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MusicInfoViewModel(repository, musicInfoDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}