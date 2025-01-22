package com.example.finalproject092.repository

import com.example.finalproject092.model.Anggota
import com.example.finalproject092.service_api.AnggotaApiService
import java.io.IOException

interface AnggotaRepository {
    suspend fun getAllAnggota(): List<Anggota>
    suspend fun getAnggotabyId(idAnggota: String): Anggota
    suspend fun insertAnggota(anggota: Anggota)
    suspend fun updateAnggota(idAnggota: String, anggota: Anggota)
    suspend fun deleteAnggota(idAnggota: String)
}

class AnggotaNetworkRepository(
    private val anggotaApiService: AnggotaApiService
) : AnggotaRepository {
    override suspend fun getAllAnggota(): List<Anggota> = anggotaApiService.getAllAnggota()

    override suspend fun getAnggotabyId(idAnggota: String): Anggota {
        return anggotaApiService.getAnggotabyId(idAnggota)
    }

    override suspend fun insertAnggota(anggota: Anggota) {
        anggotaApiService.insertAnggota(anggota)
    }

    override suspend fun updateAnggota(idAnggota: String, anggota: Anggota) {
        anggotaApiService.updateAnggota(idAnggota, anggota)
    }

    override suspend fun deleteAnggota(idAnggota: String) {
        try {
            val response = anggotaApiService.deleteAnggota(idAnggota)
            if (!response.isSuccessful) {
                throw IOException("Failed to delete Anggota. HTTP Status code:" +
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