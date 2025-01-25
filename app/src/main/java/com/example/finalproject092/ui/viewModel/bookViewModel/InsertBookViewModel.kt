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
    private val bukuRepo : BukuRepository
): ViewModel() {
    var uiState by mutableStateOf(InsertBookUiState())
        private set

    fun updateInsertBukuState(insertBookUiEvent: InsertBookUiEvent){
        uiState = InsertBookUiState(insertBookUiEvent = insertBookUiEvent)
    }

    suspend fun insertBook(){
        viewModelScope.launch {
            try {
                bukuRepo.insertBuku(uiState.insertBookUiEvent.toBk())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}

fun InsertBookUiEvent.toBk(): Buku = Buku(
    idBuku = "",
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)

fun Buku.toUiStateBook(): InsertBookUiState = InsertBookUiState(
    insertBookUiEvent = toInsertBookUiEvent()
)

fun Buku.toInsertBookUiEvent(): InsertBookUiEvent = InsertBookUiEvent(
    idBuku = "",
    judul = judul,
    penulis = penulis,
    kategori = kategori,
    status = status
)

data class InsertBookUiState(
    val insertBookUiEvent: InsertBookUiEvent = InsertBookUiEvent()
)

data class InsertBookUiEvent(
    val idBuku: String = "",
    val judul: String = "",
    val penulis: String = "",
    val kategori: String = "",
    val status: String = ""
)