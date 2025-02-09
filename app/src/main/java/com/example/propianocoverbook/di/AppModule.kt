package com.example.propianocoverbook.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.propianocoverbook.data.MusicInfoDao
import com.example.propianocoverbook.data.MusicInfoDatabase
import com.example.propianocoverbook.data.MusicInfoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideMusicInfoDatabase(
        @ApplicationContext context: Context
    ): MusicInfoDatabase {
        return Room.databaseBuilder(
            context,
            MusicInfoDatabase::class.java,
            "music_info"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideMusicInfoDao(
        musicInfoDatabase: MusicInfoDatabase
    ): MusicInfoDao {
        return musicInfoDatabase.musicInfoDao()
    }

    @Singleton
    @Provides
    @MusicDataStore
    fun provideMusicDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile("musicDataScreen")
            }
        )
    }
}

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    fun provideMusicInfoRepository(
        musicInfoDao: MusicInfoDao
    ): MusicInfoRepository {
        return MusicInfoRepository(musicInfoDao)
    }
}

@Qualifier
annotation class MusicDataStore
