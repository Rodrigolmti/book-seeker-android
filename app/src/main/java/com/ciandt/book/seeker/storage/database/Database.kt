package com.ciandt.book.seeker.storage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ciandt.book.seeker.storage.dao.BookDao
import com.ciandt.book.seeker.storage.model.HistoricStorage

private const val DATABASE_NAME = "book_database"

@Database(entities = [HistoricStorage::class], version = 1)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}

interface AppDatabase {

    fun database(): BookDatabase
}

class AppDatabaseImpl(private val context: Context) : AppDatabase {

    override fun database(): BookDatabase = Room.databaseBuilder(
        context.applicationContext,
        BookDatabase::class.java, DATABASE_NAME
    ).build()
}