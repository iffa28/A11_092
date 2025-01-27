package com.example.finalproject092.ui.viewModel.pengembalianViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Pengembalian
import com.example.finalproject092.repository.PengembalianRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class ReturnUiState{
    data class Success(val pengembalian: List<Pengembalian>) : ReturnUiState()
    object Error : ReturnUiState()
    object Loading : ReturnUiState()
}

class HomePengembalianViewModel(
    private val pengembalianRepo: PengembalianRepository
) : ViewModel() {
    var returnBookUiState: ReturnUiState by mutableStateOf(ReturnUiState.Loading)
        private set

    init {
        getReturnBookData()
    }

    fun getReturnBookData() {
        viewModelScope.launch {
            returnBookUiState = ReturnUiState.Loading
            returnBookUiState = try {
                ReturnUiState.Success(pengembalianRepo.getAllReturned())
            } catch (e: IOException) {
                ReturnUiState.Error
            } catch (e: HttpException) {
                ReturnUiState.Error

            }
        }
    }

}