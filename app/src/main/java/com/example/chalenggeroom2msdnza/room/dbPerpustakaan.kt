package com.example.chalenggeroom2msdnza.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [tbBuku::class], version = 1)
abstract class dbPerpustakaan : RoomDatabase(){
    abstract fun tbBookDAO(): tbBookDAO

    companion object {

        @Volatile
        private var instance: dbPerpustakaan? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            dbPerpustakaan::class.java,
            "smksa.db"
        ).build()
    }
}