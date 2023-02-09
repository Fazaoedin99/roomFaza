package com.example.chalenggeroom2msdnza.room

import androidx.room.*

@Dao
interface tbBookDAO {
    @Insert
    fun addtbbuku(tbbuk: tbBuku)

    @Update
    fun updatetbbuku(tbbuk: tbBuku)

    @Delete
    fun delttbbuku(tbbuk: tbBuku)

    @Query("SELECT * FROM tbBuku")
    fun tampilsemua(): List<tbBuku>

    @Query("SELECT * FROM tbBuku WHERE id_buku =:tbbuk_id")
    suspend fun tampilid(tbbuk_id: Int): List<tbBuku>
}