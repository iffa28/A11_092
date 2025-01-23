package com.example.finalproject092.service_api

import com.example.finalproject092.model.Buku
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface BukuApiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("getAllBuku.php")
    suspend fun getAllBuku(): List<Buku>

    @GET("get1Buku.php")
    suspend fun  getBukubyId(@Query("id_buku") idBuku: Int): Buku

    @POST("insertBuku.php")
    suspend fun insertBuku(@Body buku: Buku)

    @PUT("updateBuku.php/{id_buku}")
    suspend fun updateBuku(@Query("id_buku") idBuku: Int, @Body buku: Buku)

    @DELETE("deleteBuku.php/{id_buku}")
    suspend fun deleteBuku(@Query("id_buku") idBuku: Int): Response<Void>
}