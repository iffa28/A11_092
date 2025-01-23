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
    private val bukuRepo : BukuRepository
) : ViewModel() {
    var updateBookUiState by mutableStateOf(InsertBookUiState())
        private set
    val idBuku: Int = checkNotNull(savedStateHandle[UpdateBookDestination.idBook])

    init {
        viewModelScope.launch {
            updateBookUiState = bukuRepo.getBukubyId(idBuku).toUiStateBook()
        }
    }

    fun updateInsertBookState(insertBookUiEvent: InsertBookUiEvent){
        updateBookUiState = InsertBookUiState(insertBookUiEvent = insertBookUiEvent)
    }

    suspend fun  updateBookData(){
        viewModelScope.launch {
            try {
                bukuRepo.updateBuku(idBuku, updateBookUiState.insertBookUiEvent.toBk())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}