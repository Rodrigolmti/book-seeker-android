package com.ciandt.book.seeker.storage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "historic")
class HistoricStorage(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val query: String
)