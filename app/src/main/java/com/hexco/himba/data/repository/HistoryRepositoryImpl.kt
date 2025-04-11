package com.hexco.himba.data.repository

import com.hexco.himba.data.local.HistoryDao
import com.hexco.himba.domain.history.History
import com.hexco.himba.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow

class HistoryRepositoryImpl(
  val historyDao: HistoryDao
) : HistoryRepository {
  override fun getAll(): Flow<List<History>> =
    historyDao.getAll()

  override suspend fun addHistory(history: History) {
    historyDao.insertHistory(history)
  }

  override suspend fun removeHistory(query: String) {
    historyDao.removeHistory(query)
  }
}