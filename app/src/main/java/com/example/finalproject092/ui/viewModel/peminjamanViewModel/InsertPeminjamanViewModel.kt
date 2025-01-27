package com.example.finalproject092.ui.viewModel.peminjamanViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Anggota
import com.example.finalproject092.model.Buku
import com.example.finalproject092.model.Peminjaman
import com.example.finalproject092.repository.AnggotaRepository
import com.example.finalproject092.repository.BukuRepository
import com.example.finalproject092.repository.PeminjamanRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class InsertPeminjamanViewModel(
    private val peminjamanRepo : PeminjamanRepository,
    private val bukuRepo: BukuRepository,
    private val anggotaRepo: AnggotaRepository
): ViewModel() {
    var uiState by mutableStateOf(InsertPjUiState())
        private set

    var bukuList by mutableStateOf<List<Buku>>(emptyList())
        private set
    var bookTitles by mutableStateOf<List<String>>(emptyList())
        private set

    var anggotaList by mutableStateOf<List<Anggota>>(emptyList())
        private set
    var namaAnggota by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        fetchBukuList()
    }

    private fun fetchBukuList() {
        viewModelScope.launch {
            try {
                // Ambil daftar buku dari repository
                bukuList = bukuRepo.getAllBuku()// Misalnya, fungsi ini mengembalikan daftar judul buku
                bookTitles = bukuList.map { it.judul }
                uiState = uiState.copy(bukuList = bukuList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    init {
        fetchAnggotaList()
    }

    // Fungsi untuk mengambil daftar buku
    private fun fetchAnggotaList() {
        viewModelScope.launch {
            try {
                anggotaList = anggotaRepo.getAllAnggota()// Misalnya, fungsi ini mengembalikan daftar judul buku
                namaAnggota = anggotaList.map { it.nama}
                uiState = uiState.copy(anggotaList = anggotaList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertPeminjamanState(insertPjUiEvent: InsertPjUiEvent){
        uiState = uiState.copy(insertPjUiEvent = insertPjUiEvent)
    }

    suspend fun insertPeminjaman(){
        viewModelScope.launch {
            try {
                peminjamanRepo.insertPinjam(uiState.insertPjUiEvent.toPj())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}

fun InsertPjUiEvent.toPj(): Peminjaman{
    val validTanggalPeminjaman = tanggalPeminjaman ?: throw IllegalArgumentException("Tanggal peminjaman harus diisi.")
    val validTanggalPengembalian = tanggalPengembalian ?: throw IllegalArgumentException("Tanggal pengembalian harus diisi.")
    return Peminjaman(
        idPeminjaman = idPeminjaman,
        idBuku = idBuku,
        judul = judul,
        idAnggota = idAnggota,
        nama = nama,
        tanggalPeminjaman = validTanggalPeminjaman,
        tanggalPengembalian = validTanggalPengembalian,
        status = status)
}



fun Peminjaman.toUiStatePj(): InsertPjUiState = InsertPjUiState(
    insertPjUiEvent = toInsertPjUiEvent()
)

fun Peminjaman.toInsertPjUiEvent(): InsertPjUiEvent = InsertPjUiEvent(
    idPeminjaman = idPeminjaman,
    idBuku = idBuku,
    judul = judul,
    idAnggota = idAnggota,
    nama = nama,
    tanggalPeminjaman = tanggalPeminjaman,
    tanggalPengembalian = tanggalPengembalian,
    status = status
)

data class InsertPjUiState(
    val bukuList: List<Buku> = emptyList(),
    val anggotaList: List<Anggota> = emptyList(),
    val insertPjUiEvent: InsertPjUiEvent = InsertPjUiEvent()
)


data class InsertPjUiEvent(
    val idPeminjaman: Int = 0,
    val idBuku: String = "",
    val judul: String = "",
    val idAnggota: String = "",
    val nama: String = "",
    val tanggalPeminjaman: LocalDate? = null,
    val tanggalPengembalian: LocalDate? = null,
    val status: String = ""
)