package com.example.finalproject092.depedenciesInjection

import com.example.finalproject092.repository.AnggotaNetworkRepository
import com.example.finalproject092.repository.AnggotaRepository
import com.example.finalproject092.repository.BukuNetworkRepository
import com.example.finalproject092.repository.BukuRepository
import com.example.finalproject092.repository.PeminjamanNetworkRepository
import com.example.finalproject092.repository.PeminjamanRepository
import com.example.finalproject092.repository.PengembalianNetworkRepository
import com.example.finalproject092.repository.PengembalianRepository
import com.example.finalproject092.service_api.AnggotaApiService
import com.example.finalproject092.service_api.BukuApiService
import com.example.finalproject092.service_api.PeminjamanApiService
import com.example.finalproject092.service_api.PengembalianApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer{
    val bukuRepository: BukuRepository
    val anggotaRepository: AnggotaRepository
    val peminjamanRepository: PeminjamanRepository
    val pengembalianRepository: PengembalianRepository
}

class LibraryAppContainer : AppContainer{
    // URL dasar untuk mengakses API
    private val baseUrl = "http://10.0.2.2:8000/perpustakaan/"

    // Mengatur JSON parser agar bisa mengabaikan data yang tidak dikenal di response
    private val json = Json { ignoreUnknownKeys = true }

    // Membuat objek Retrofit untuk menghubungkan aplikasi dengan API
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)   // Mengatur alamat dasar API
        .build()  // Membangun objek Retrofit

    // Objek ini digunakan untuk mengakses layanan API buku
    private val bukuApiService: BukuApiService by lazy {
        retrofit.create(BukuApiService::class.java)
    }

    private val anggotaAPIService: AnggotaApiService by lazy {
        retrofit.create(AnggotaApiService::class.java)
    }
    private val peminjamanApiService: PeminjamanApiService by lazy {
        retrofit.create(PeminjamanApiService::class.java)
    }
    private val pengembalianApiService: PengembalianApiService by lazy {
        retrofit.create(PengembalianApiService::class.java)
    }

    override val bukuRepository: BukuRepository by lazy {
        BukuNetworkRepository(bukuApiService)
    }

    //mengelola repository Anggota
    override val anggotaRepository: AnggotaRepository by lazy {
        AnggotaNetworkRepository(anggotaAPIService)
    }

    //mengelola repository Peminjaman
    override val peminjamanRepository: PeminjamanRepository by lazy {
        PeminjamanNetworkRepository(peminjamanApiService)
    }

    override val pengembalianRepository: PengembalianRepository  by lazy {
        PengembalianNetworkRepository(pengembalianApiService)
    }

}