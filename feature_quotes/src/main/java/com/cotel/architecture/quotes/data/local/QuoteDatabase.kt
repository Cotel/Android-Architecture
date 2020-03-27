package com.cotel.architecture.quotes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cotel.architecture.quotes.data.dto.DbQuote

@Database(entities = [DbQuote::class], version = 1)
abstract class QuoteDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDao

    companion object {
        private const val DATABASE_NAME = "database-quote"

        operator fun invoke(applicationContext: Context): QuoteDatabase =
            Room.databaseBuilder(
                applicationContext,
                QuoteDatabase::class.java,
                DATABASE_NAME
            ).build()
    }
}
