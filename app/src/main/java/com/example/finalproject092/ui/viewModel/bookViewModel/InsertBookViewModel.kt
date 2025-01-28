package com.example.finalproject092.ui.viewModel.bookViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Buku
import com.example.finalproject092.repository.BukuRepository
import kotlinx.coroutines.launch

class InsertBookViewModel(
    private val bukuRepo: BukuRepository
) : ViewModel() {

    var uiState by mutableStateOf(InsertBookUiState())
        private set

    fun updateInsertBukuState(insertBookUiEvent: InsertBookUiEvent) {
        uiState = InsertBookUiState(insertBookUiEvent = insertBookUiEvent)
    }

    // Fungsi untuk memvalidasi input dan menyimpan buku jika valid
    fun validateAndInsertBook() {
        val validationResult = validateInput(uiState.insertBookUiEvent)

        if (validationResult.isValid) {
            viewModelScope.launch {
                try {
                    bukuRepo.insertBuku(uiState.insertBookUiEvent.toBk())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            // Update state untuk menampilkan pesan kesalahan
            uiState = uiState.copy(validationErrors = validationResult.errors)
        }
    }

    private fun validateInput(event: InsertBookUiEvent): ValidationResult {
        val errors = mutableListOf<String>()

        if (event.judul.isEmpty()) errors.add("Judul buku tidak boleh kosong")
        if (event.penulis.isEmpty()) errors.add("Penulis tidak boleh kosong")
        if (event.kategori.isEmpty()) errors.add("Kategori tidak boleh kosong")
        if (event.status.isEmpty()) errors.add("Status tidak boleh kosong")

        return ValidationResult(errors.isEmpty(), errors)
    }
}

// Data class untuk menyimpan hasil validasi
data class ValidationResult(val isValid: Boolean, val errors: List<String>)


fun InsertBookUiEvent.toBk(): Buku = Buku(
    idBuku = "",
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)

data class InsertBookUiState(
    val insertBookUiEvent: InsertBookUiEvent = InsertBookUiEvent(),
    val validationErrors: List<String> = emptyList()
)

data class InsertBookUiEvent(
    val idBuku: String = "",
    val judul: String = "",
    val penulis: String = "",
    val kategori: String = "",
    val status: String = "",
    val isValid: Boolean = true // Menambahkan status validasi
)
