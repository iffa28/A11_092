package com.example.finalproject092.ui.viewModel.peminjamanViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Peminjaman
import com.example.finalproject092.repository.PeminjamanRepository
import com.example.finalproject092.ui.navigation.DetailPeminjamanDestination
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPjUiState {
    data class Success(val peminjaman: Peminjaman) : DetailPjUiState()
    object Error : DetailPjUiState()
    object Loading : DetailPjUiState()
}

class DetailPeminjamanViewModel(
    savedStateHandle: SavedStateHandle,
    private val peminjamanRepo: PeminjamanRepository
) : ViewModel() {

    private val idPeminjaman: Int = checkNotNull(savedStateHandle[DetailPeminjamanDestination.idPj])
    var detailpjUiState: DetailPjUiState by mutableStateOf(DetailPjUiState.Loading)
        private set

    init {
        getPeminjamanbyId()
    }
    fun getPeminjamanbyId() {
        viewModelScope.launch {
            detailpjUiState = DetailPjUiState.Loading
            detailpjUiState = try {
                DetailPjUiState.Success(peminjamanRepo.getPinjambyId(idPeminjaman))
            } catch (e: IOException) {
                println("Network error: ${e.message}")
                DetailPjUiState.Error
            } catch (e: HttpException) {
                println("HTTP error: ${e.message}")
                DetailPjUiState.Error
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
                DetailPjUiState.Error
            }
        }
    }

    fun deletePeminjaman(idPeminjaman: Int){
        viewModelScope.launch {
            try {
                peminjamanRepo.deletePinjam(idPeminjaman)
            } catch (e: IOException) {
                DetailPjUiState.Error
            } catch (e: HttpException) {
                DetailPjUiState.Error
            }
        }
    }

}