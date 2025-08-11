package com.example.sortielogger.db

import android.content.Context
import androidx.room.*
import com.example.sortielogger.model.Sortie
import java.util.*

@Database(entities = [Sortie::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun sortieDao(): SortieDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val inst = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "sorties.db").build()
                INSTANCE = inst
                inst
            }
        }
    }
}

class Converters {
    @TypeConverter fun fromTimestamp(value: Long?) = value?.let { Date(it) }
    @TypeConverter fun dateToTimestamp(date: Date?) = date?.time
}
