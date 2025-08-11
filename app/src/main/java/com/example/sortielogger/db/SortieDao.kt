package com.example.sortielogger.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sortielogger.model.Sortie

@Dao
interface SortieDao {
    @Insert
    suspend fun insert(s: Sortie)

    @Query("SELECT * FROM sorties ORDER BY date DESC")
    suspend fun getAll(): List<Sortie>

    @Query("DELETE FROM sorties")
    suspend fun deleteAll()
}
