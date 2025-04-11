package com.hexco.himba.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hexco.himba.domain.history.History

@Database(entities = [History::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract val historyDao: HistoryDao
}