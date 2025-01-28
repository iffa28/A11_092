package com.example.finalproject092.ui.viewModel.bookViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.repository.BukuRepository
import com.example.finalproject092.ui.navigation.UpdateBookDestination
import kotlinx.coroutines.launch

class UpdateBookViewModel(
    savedStateHandle: SavedStateHandle,
    private val bukuRepo: BukuRepository
) : ViewModel() {

    var updateBookUiState by mutableStateOf(InsertBookUiState())
        private set

    val idBuku: String = checkNotNull(savedStateHandle[UpdateBookDestination.idBook])

    init {
        // Fetch book data by idBuku and update UI state
        viewModelScope.launch {
            try {
                // Assuming getBukubyId returns a Buku object
                val buku = bukuRepo.getBukubyId(idBuku)
                updateBookUiState = InsertBookUiState(
                    insertBookUiEvent = InsertBookUiEvent(
                        idBuku = buku.idBuku,
                        judul = buku.judul,
                        penulis = buku.penulis,
                        kategori = buku.kategori,
                        status = buku.status
                    )
                )
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error (e.g., update state with error message)
            }
        }
    }

    // Update the state when the form values change
    fun updateInsertBookState(insertBookUiEvent: InsertBookUiEvent) {
        updateBookUiState = updateBookUiState.copy(insertBookUiEvent = insertBookUiEvent)
    }

    // Update the book data in the repository
    fun updateBookData() {
        viewModelScope.launch {
            try {
                bukuRepo.updateBuku(idBuku, updateBookUiState.insertBookUiEvent.toBk())
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error (e.g., update state with error message)
            }
        }
    }
}
