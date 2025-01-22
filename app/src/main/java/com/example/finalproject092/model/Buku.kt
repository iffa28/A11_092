package com.example.finalproject092.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Buku (
    @SerialName("id_buku")
    val idBuku: Int,
    val judul: String,
    val penulis: String,
    val kategori: String,
    val status: String
)