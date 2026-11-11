package com.crafted.castle.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.crafted.castle.data.model.SearchHistory

@Database(
    entities = [SearchHistory::class],
    version = 1,
    exportSchema = false
)
abstract class CastleDatabase : RoomDatabase() {
    abstract fun contentDao(): ContentDao
}