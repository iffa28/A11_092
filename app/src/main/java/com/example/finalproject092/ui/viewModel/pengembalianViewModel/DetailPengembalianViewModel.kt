package com.example.finalproject092.ui.viewModel.pengembalianViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Pengembalian
import com.example.finalproject092.repository.PengembalianRepository
import com.example.finalproject092.ui.navigation.DetailPengembalianDestination
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailPgUiState {
    data class Success(val pengembalian: Pengembalian) : DetailPgUiState()
    object Error : DetailPgUiState()
    object Loading : DetailPgUiState()
}

class DetailPengembalianViewModel(
    savedStateHandle: SavedStateHandle,
    private val pengembalianRepo: PengembalianRepository
) : ViewModel() {

    private val idReturn: Int = checkNotNull(savedStateHandle[DetailPengembalianDestination.idPg])
    var detailPgUiState: DetailPgUiState by mutableStateOf(DetailPgUiState.Loading)
        private set

    init {
        getPengembalianbyId()
    }
    fun getPengembalianbyId() {
        viewModelScope.launch {
            detailPgUiState = DetailPgUiState.Loading
            detailPgUiState = try {
                DetailPgUiState.Success(pengembalianRepo.getReturnedbyId(idReturn))
            } catch (e: IOException) {
                println("Network error: ${e.message}")
                DetailPgUiState.Error
            } catch (e: HttpException) {
                println("HTTP error: ${e.message}")
                DetailPgUiState.Error
            } catch (e: Exception) {
                println("Unexpected error: ${e.message}")
                DetailPgUiState.Error
            }
        }
    }

    fun deletePengembalian(idReturn: Int){
        viewModelScope.launch {
            try {
                pengembalianRepo.deleteReturned(idReturn)
            } catch (e: IOException) {
                DetailPgUiState.Error
            } catch (e: HttpException) {
                DetailPgUiState.Error
            }
        }
    }

}