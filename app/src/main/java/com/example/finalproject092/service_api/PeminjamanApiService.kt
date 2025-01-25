package com.example.finalproject092.service_api

import com.example.finalproject092.model.Peminjaman
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PeminjamanApiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("getAllPeminjaman.php")
    suspend fun getAllPeminjaman(): List<Peminjaman>

    @GET("get1Peminjaman.php")
    suspend fun  getPeminjamanbyId(@Query("id_peminjaman") idPeminjaman: Int): Peminjaman

    @POST("insertPeminjaman.php")
    suspend fun insertPeminjaman(@Body peminjaman: Peminjaman)

    @PUT("updatePeminjaman.php/{id_peminjaman}")
    suspend fun updatePeminjaman(@Query("id_peminjaman") idPeminjaman: Int, @Body peminjaman: Peminjaman)

    @DELETE("deletePeminjaman.php/{id_peminjaman}")
    suspend fun deletePeminjaman(@Query("id_peminjaman") idPeminjaman: Int): Response<Void>

}