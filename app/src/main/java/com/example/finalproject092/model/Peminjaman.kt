package com.example.finalproject092.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
data class Peminjaman (
    @SerialName("id_peminjaman")
    val idPeminjaman: Int,

    @SerialName("id_buku")
    val idBuku: String,

    val judul: String,
    val status : String,

    @SerialName("id_anggota")
    val idAnggota: String,

    val nama: String,

    @SerialName("tanggal_peminjaman")
    @Serializable(with = LocalDateSerializer::class)
    val tanggalPeminjaman: LocalDate,

    @SerialName("tanggal_pengembalian")
    @Serializable(with = LocalDateSerializer::class)
    val tanggalPengembalian: LocalDate


)
