package com.example.finalproject092.ui.viewModel.bookViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Buku
import com.example.finalproject092.repository.BukuRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class BookUiState{
    data class Success(val buku: List<Buku>) : BookUiState()
    object Error : BookUiState()
    object Loading : BookUiState()
}

class HomeBookViewModel(
    private val bukuRepository: BukuRepository
) : ViewModel() {
    var bukuUiState: BookUiState by mutableStateOf(BookUiState.Loading)
        private set

    init {
        getDataBook()
    }

    fun getDataBook() {
        viewModelScope.launch {
            bukuUiState = BookUiState.Loading
            bukuUiState = try {
                BookUiState.Success(bukuRepository.getAllBuku())
            } catch (e: IOException) {
                BookUiState.Error
            } catch (e: HttpException) {
                BookUiState.Error

            }
        }
    }

    fun deleteBook(idBuku: Int){
        viewModelScope.launch {
            try {
                bukuRepository.deleteBuku(idBuku)
            } catch (e: IOException) {
                DetailBookUiState.Error
            } catch (e: HttpException) {
                DetailBookUiState.Error
            }
        }
    }

}