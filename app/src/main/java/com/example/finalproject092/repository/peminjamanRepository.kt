package com.example.finalproject092.repository

import com.example.finalproject092.model.Peminjaman
import com.example.finalproject092.service_api.PeminjamanApiService
import java.io.IOException

interface PeminjamanRepository {
    suspend fun getAllPinjam(): List<Peminjaman>
    suspend fun getPinjambyId(idPeminjaman: Int): Peminjaman
    suspend fun insertPinjam(peminjaman: Peminjaman)
    suspend fun updatePinjam(idPeminjaman: Int, peminjaman: Peminjaman)
    suspend fun deletePinjam(idPeminjaman: Int)
}

class PeminjamanNetworkRepository(
    private val peminjamanApiService: PeminjamanApiService
) : PeminjamanRepository {
    override suspend fun getAllPinjam(): List<Peminjaman> = peminjamanApiService.getAllPeminjaman()

    override suspend fun getPinjambyId(idPeminjaman: Int): Peminjaman {
        return peminjamanApiService.getPeminjamanbyId(idPeminjaman)
    }

    override suspend fun insertPinjam(peminjaman: Peminjaman) {
        peminjamanApiService.insertPeminjaman(peminjaman)
    }

    override suspend fun updatePinjam(idPeminjaman: Int, peminjaman: Peminjaman) {
        peminjamanApiService.updatePeminjaman(idPeminjaman, peminjaman)
    }

    override suspend fun deletePinjam(idPeminjaman: Int) {
        try {
            val response = peminjamanApiService.deletePeminjaman(idPeminjaman)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Peminjaman. HTTP Status code:" +
                        "${response.code()}")
            } else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception){
            throw e
        }
    }
}