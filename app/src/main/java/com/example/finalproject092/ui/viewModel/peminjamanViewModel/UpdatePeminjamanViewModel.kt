package com.example.finalproject092.ui.viewModel.peminjamanViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Anggota
import com.example.finalproject092.model.Buku
import com.example.finalproject092.repository.AnggotaRepository
import com.example.finalproject092.repository.BukuRepository
import com.example.finalproject092.repository.PeminjamanRepository
import com.example.finalproject092.ui.navigation.UpdatePeminjamanDestination
import kotlinx.coroutines.launch

class UpdatePeminjamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val peminjamanRepo : PeminjamanRepository,
    private val bukuRepo: BukuRepository,
    private val anggotaRepo: AnggotaRepository
) : ViewModel() {
    var updatePjUiState by mutableStateOf(InsertPjUiState())
        private set

    var bukuList by mutableStateOf<List<Buku>>(emptyList())
        private set
    var bookTitles by mutableStateOf<List<String>>(emptyList())
        private set

    var anggotaList by mutableStateOf<List<Anggota>>(emptyList())
        private set
    var namaAnggota by mutableStateOf<List<String>>(emptyList())
        private set

    var selectedBuku by mutableStateOf("")

    init {
        fetchBukuList() // Mengambil daftar buku saat ViewModel diinisialisasi
    }

    // Fungsi untuk mengambil daftar buku
    private fun fetchBukuList() {
        viewModelScope.launch {
            try {
                // Ambil daftar buku dari repository
                bukuList = bukuRepo.getAllBuku()// Misalnya, fungsi ini mengembalikan daftar judul buku
                bookTitles = bukuList.map { it.judul }
                selectedBuku = updatePjUiState.insertPjUiEvent.judul
                updatePjUiState = updatePjUiState.copy(bukuList = bukuList)

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
                updatePjUiState = updatePjUiState.copy(anggotaList = anggotaList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    val idPeminjaman: Int = checkNotNull(savedStateHandle[UpdatePeminjamanDestination.idPj])

    init {
        viewModelScope.launch {
            val peminjaman  = peminjamanRepo.getPinjambyId(idPeminjaman)
                updatePjUiState = peminjaman.toUiStatePj()
        }
    }

    fun updateInsertPjState(insertPjUiEvent: InsertPjUiEvent){
        updatePjUiState = InsertPjUiState(insertPjUiEvent = insertPjUiEvent)
    }

    suspend fun  updatePeminjamanData(){
        viewModelScope.launch {
            try {
                peminjamanRepo.updatePinjam(idPeminjaman, updatePjUiState.insertPjUiEvent.toPj().copy(idBuku = selectedBuku))
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}