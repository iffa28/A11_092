package com.example.finalproject092.repository

import com.example.finalproject092.model.Buku
import com.example.finalproject092.service_api.BukuApiService
import java.io.IOException

interface BukuRepository {
    suspend fun getAllBuku(): List<Buku>
    suspend fun getBukubyId(idBuku: String): Buku
    suspend fun insertBuku(buku: Buku)
    suspend fun updateBuku(idBuku: String, buku: Buku)
    suspend fun deleteBuku(idBuku: String)
}

class BukuNetworkRepository(
    private val bukuApiService: BukuApiService
) : BukuRepository {
    override suspend fun getAllBuku(): List<Buku> = bukuApiService.getAllBuku()

    override suspend fun getBukubyId(idBuku: String): Buku {
        return bukuApiService.getBukubyId(idBuku)
    }

    override suspend fun insertBuku(buku: Buku) {
        bukuApiService.insertBuku(buku)
    }

    override suspend fun updateBuku(idBuku: String, buku: Buku) {
        bukuApiService.updateBuku(idBuku, buku)
    }

    override suspend fun deleteBuku(idBuku: String) {
        try {
            val response = bukuApiService.deleteBuku(idBuku)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Buku. HTTP Status code:" +
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