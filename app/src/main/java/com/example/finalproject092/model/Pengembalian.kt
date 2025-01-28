package com.example.finalproject092.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Pengembalian (
    @SerialName("id_pengembalian")
    val idReturn: Int,

    @SerialName("id_peminjaman")
    val idPeminjaman: Int,
    val nama: String,
    val judul: String,

    @SerialName("id_anggota")
    val idAnggota: String,
    @SerialName("tanggal_dikembalikan")

    @Serializable(with = LocalDateSerializer::class)
    val tglDikembalikan: LocalDate,

    val denda: Int
)