package com.example.finalproject092.service_api
import com.example.finalproject092.model.Pengembalian
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface PengembalianApiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )

    @GET("getAllPengembalian.php")
    suspend fun getAllPengembalian(): List<Pengembalian>

    @GET("get1Pengembalian.php")
    suspend fun  getPengembalianbyId(@Query("id_pengembalian") idReturn: Int): Pengembalian

    @POST("insertPengembalian.php")
    suspend fun insertPengembalian(@Body pengembalian: Pengembalian)

    @PUT("updatePengembalian.php/{id_pengembalian}")
    suspend fun updatePengembalian(@Query("id_pengembalian") idReturn: Int, @Body pengembalian: Pengembalian)

    @DELETE("deletePengembalian.php/{id_pengembalian}")
    suspend fun deletePengembalian(@Query("id_pengembalian") idReturn: Int): Response<Void>
}