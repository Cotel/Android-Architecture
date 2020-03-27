package com.cotel.architecture.quotes.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.cotel.architecture.quotes.data.dto.DbQuote

@Dao
interface QuoteDao {
    @Insert
    suspend fun insert(quote: DbQuote)

    @Query("SELECT * FROM quote")
    suspend fun queryAll(): List<DbQuote>

    @Query("SELECT * FROM quote WHERE id == :id")
    suspend fun queryById(id: String): DbQuote?

    @Delete
    suspend fun remove(quote: DbQuote)
}
