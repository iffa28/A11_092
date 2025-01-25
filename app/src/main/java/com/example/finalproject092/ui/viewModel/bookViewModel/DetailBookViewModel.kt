package com.example.finalproject092.ui.viewModel.bookViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Buku
import com.example.finalproject092.repository.BukuRepository
import com.example.finalproject092.ui.navigation.DetailBookDestination
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailBookUiState {
    data class Success(val buku: Buku) : DetailBookUiState()
    object Error : DetailBookUiState()
    object Loading : DetailBookUiState()
}

// ViewModel untuk mengelola data dan status UI pada DetailMhs
class DetailBookViewModel(
    savedStateHandle: SavedStateHandle,
    private val bukuRepo: BukuRepository
) : ViewModel() {

    // Mendapatkan NIM dari `SavedStateHandle`. Jika tidak ada, akan memunculkan exception.
    private val idBuku: String = checkNotNull(savedStateHandle[DetailBookDestination.idBook])
    var detailBookUiState: DetailBookUiState by mutableStateOf(DetailBookUiState.Loading)
        private set

    init {
        getBookbyId()
    }

    // Fungsi untuk mengambil data mahasiswa berdasarkan NIM
    fun getBookbyId() {
        viewModelScope.launch {
            detailBookUiState = DetailBookUiState.Loading
            detailBookUiState = try {
                DetailBookUiState.Success(bukuRepo.getBukubyId(idBuku))
            } catch (e: Exception) {
                DetailBookUiState.Error
            }
        }

    }

    fun deleteBook(idBuku: String){
        viewModelScope.launch {
            try {
                bukuRepo.deleteBuku(idBuku)
            } catch (e: IOException) {
                DetailBookUiState.Error
            } catch (e: HttpException) {
                DetailBookUiState.Error
            }
        }
    }

}