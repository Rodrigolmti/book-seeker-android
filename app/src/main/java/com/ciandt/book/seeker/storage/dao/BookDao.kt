package com.ciandt.book.seeker.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ciandt.book.seeker.storage.model.HistoricStorage

@Dao
interface BookDao {

    @Insert
    fun insert(book: HistoricStorage)

    @Query("SELECT * FROM historic")
    fun findAll(): List<HistoricStorage>

    @Query("DELETE FROM historic")
    fun deleteAll()
}