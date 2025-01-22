package com.example.finalproject092.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Anggota (
    @SerialName("id_anggota")
    val idAnggota: String,

    val nama: String,
    val email: String,

    @SerialName("nomor_telepon")
    val nomorTelepon: String
)
