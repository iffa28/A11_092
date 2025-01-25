package com.example.finalproject092.repository

import com.example.finalproject092.model.Pengembalian
import com.example.finalproject092.service_api.PengembalianApiService
import java.io.IOException

interface PengembalianRepository {
    suspend fun getAllReturned(): List<Pengembalian>
    suspend fun getReturnedbyId(idReturn: Int): Pengembalian
    suspend fun insertReturned(pengembalian: Pengembalian)
    suspend fun updateReturned(idReturn: Int, pengembalian: Pengembalian)
    suspend fun deleteReturned(idReturn: Int)
}

class PengembalianNetworkRepository(
    private val pengembalianApiService: PengembalianApiService
) : PengembalianRepository {
    override suspend fun getAllReturned(): List<Pengembalian> = pengembalianApiService.getAllPengembalian()

    override suspend fun getReturnedbyId(idReturn: Int): Pengembalian {
        return pengembalianApiService.getPengembalianbyId(idReturn)
    }

    override suspend fun insertReturned(pengembalian: Pengembalian) {
        pengembalianApiService.insertPengembalian(pengembalian)
    }

    override suspend fun updateReturned(idReturn: Int, pengembalian: Pengembalian) {
        pengembalianApiService.updatePengembalian(idReturn, pengembalian)
    }

    override suspend fun deleteReturned(idReturn: Int) {
        try {
            val response = pengembalianApiService.deletePengembalian(idReturn)
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