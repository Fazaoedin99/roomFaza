package com.example.chalenggeroom2msdnza.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class tbBuku(
    @PrimaryKey
    val id_buku: Int,
    val kategori: Int,
    val judul: String,
    val pengarang: String,
    val penerbit: String,
    val jml_buku:String
)