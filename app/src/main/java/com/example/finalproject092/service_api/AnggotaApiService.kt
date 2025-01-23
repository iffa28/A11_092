package com.example.finalproject092.service_api

import com.example.finalproject092.model.Anggota
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface AnggotaApiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("getAllAnggota.php")
    suspend fun getAllAnggota(): List<Anggota>

    @GET("get1Anggota.php")
    suspend fun  getAnggotabyId(@Query("id_anggota") idAnggota: String): Anggota

    @POST("insertAnggota.php")
    suspend fun insertAnggota(@Body anggota: Anggota)

    @PUT("updateAnggota.php/{id_anggota}")
    suspend fun updateAnggota(@Query("id_anggota") idAnggota: String, @Body anggota: Anggota)

    @DELETE("deleteAnggota.php/{id_anggota}")
    suspend fun deleteAnggota(@Query("id_anggota") idAnggota: String): Response<Void>
}