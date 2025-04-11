package com.hexco.himba.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hexco.himba.domain.history.History
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
  @Query("SELECT * FROM history ORDER BY `id` DESC")
  fun getAll(): Flow<List<History>>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insertHistory(history: History)

  @Query("DELETE FROM history WHERE `query` = :query")
  suspend fun removeHistory(query: String)
}