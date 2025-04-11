package com.hexco.himba.domain.history

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = [Index(value = ["query"], unique = true)])
data class History(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val query: String,
  val date: Long
)
