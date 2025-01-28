package com.example.finalproject092.ui.viewModel.peminjamanViewModel

import android.util.Log
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
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.toPg
import kotlinx.coroutines.launch

class UpdatePeminjamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val peminjamanRepo : PeminjamanRepository,
    private val bukuRepo: BukuRepository,
    private val anggotaRepo: AnggotaRepository
) : ViewModel() {
    var updatePjUiState by mutableStateOf(InsertPjUiState())
        private set

    val idPeminjaman: Int = checkNotNull(savedStateHandle[UpdatePeminjamanDestination.idPj])

    var bukuList by mutableStateOf<List<Buku>>(emptyList())
        private set
    var bookTitles by mutableStateOf<String>("")
        private set

    var anggotaList by mutableStateOf<List<Anggota>>(emptyList())
        private set
    var namaAnggota by mutableStateOf<String>("")
        private set

    init {
        fetchBukuList()
    }

    // Fungsi untuk mengambil daftar buku
    private fun fetchBukuList() {
        viewModelScope.launch {
            try {
                bukuList = bukuRepo.getAllBuku()
                bookTitles = bukuList.find { it.idBuku == idPeminjaman.toString()}?. judul ?: ""
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
                anggotaList = anggotaRepo.getAllAnggota()
                namaAnggota = anggotaList.find { it.idAnggota == idPeminjaman.toString()}?. nama ?: ""
                updatePjUiState = updatePjUiState.copy(anggotaList = anggotaList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }




    init {
        viewModelScope.launch {
            bukuList = bukuRepo.getAllBuku()
            anggotaList = anggotaRepo.getAllAnggota()

            updatePjUiState = peminjamanRepo.getPinjambyId(idPeminjaman).toUiStatePj()
        }
    }

    fun updateInsertPjState(insertPjUiEvent: InsertPjUiEvent){
        updatePjUiState = InsertPjUiState(insertPjUiEvent = insertPjUiEvent)
    }

    suspend fun  updatePeminjamanData(){
        viewModelScope.launch {
            try {
                peminjamanRepo.updatePinjam(idPeminjaman, updatePjUiState.insertPjUiEvent.toPj())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}