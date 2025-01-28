package com.example.finalproject092.ui.viewModel.pengembalianViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Peminjaman
import com.example.finalproject092.repository.PeminjamanRepository
import com.example.finalproject092.repository.PengembalianRepository
import com.example.finalproject092.ui.navigation.UpdatePengembalianDestination
import kotlinx.coroutines.launch

class UpdatePengembalianViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengembalianRepo: PengembalianRepository,
    private val peminjamanRepo: PeminjamanRepository
) : ViewModel() {
    var updatePgUiState by mutableStateOf(InsertPgUiState())
        private set

    private val idReturn: Int = checkNotNull(savedStateHandle[UpdatePengembalianDestination.idPg])

    var peminjamanList by mutableStateOf<List<Peminjaman>>(emptyList())
        private set
    var IDanggota by mutableStateOf<String>("") // Menyimpan idAnggota sebagai string
        private set

    init {
        fetchAnggotaList()
    }
    // Fungsi untuk mengambil daftar buku
    private fun fetchAnggotaList() {
        viewModelScope.launch {
            try {
                peminjamanList = peminjamanRepo.getAllPinjam()
                IDanggota = peminjamanList.find { it.idPeminjaman == idReturn }?.idAnggota ?: ""
                updatePgUiState = updatePgUiState.copy(peminjamanList = peminjamanList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    init {
        // Mengambil daftar peminjaman dan data pengembalian berdasarkan idReturn
        viewModelScope.launch {
            try {
                // Fetch Peminjaman List
                peminjamanList = peminjamanRepo.getAllPinjam()
                updatePgUiState = updatePgUiState.copy(peminjamanList = peminjamanList)

                // Fetch Pengembalian berdasarkan idReturn
                updatePgUiState = pengembalianRepo.getReturnedbyId(idReturn).toUiStatePg()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Memperbarui state UI
    fun updateInsertPgState(insertPgUiEvent: InsertPgUiEvent) {
        updatePgUiState = InsertPgUiState(insertPgUiEvent = insertPgUiEvent)
    }

    // Fungsi untuk memperbarui data pengembalian
    suspend fun updatePengembalian() {
        viewModelScope.launch {
            try {
                val pengembalian = pengembalianRepo.getReturnedbyId(idReturn)

                Log.d("UpdatePengembalianViewModel", "Data pengembalian untuk idReturn $idReturn: $pengembalian")
                updatePgUiState = pengembalian.toUiStatePg()
            } catch (e: Exception) {
                Log.e("UpdatePengembalianViewModel", "Gagal mengambil data pengembalian: ${e.message}")
            }
        }
    }
}
