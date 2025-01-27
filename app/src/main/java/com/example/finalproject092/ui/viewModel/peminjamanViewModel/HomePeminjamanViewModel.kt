package com.example.finalproject092.ui.viewModel.peminjamanViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Peminjaman
import com.example.finalproject092.repository.PeminjamanRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class BorrowUiState{
    data class Success(val peminjaman: List<Peminjaman>) : BorrowUiState()
    object Error : BorrowUiState()
    object Loading : BorrowUiState()
}

class HomePeminjamanViewModel(
    private val peminjamanRepo: PeminjamanRepository
) : ViewModel() {
    var pinjamUiState: BorrowUiState by mutableStateOf(BorrowUiState.Loading)
        private set

    init {
        getBorrowBook()
    }

    fun getBorrowBook() {
        viewModelScope.launch {
            pinjamUiState = BorrowUiState.Loading
            pinjamUiState = try {
                BorrowUiState.Success(peminjamanRepo.getAllPinjam())
            } catch (e: IOException) {
                BorrowUiState.Error
            } catch (e: HttpException) {
                BorrowUiState.Error

            }
        }
    }

}