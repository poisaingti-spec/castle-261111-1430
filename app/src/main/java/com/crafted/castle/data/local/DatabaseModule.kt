package com.crafted.castle.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CastleDatabase {
        return Room.databaseBuilder(
            context,
            CastleDatabase::class.java,
            "castle_database"
        ).build()
    }
    
    @Provides
    @Singleton
    fun provideContentDao(database: CastleDatabase): ContentDao {
        return database.contentDao()
    }
}