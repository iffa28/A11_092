package com.example.finalproject092.ui.viewModel.anggotaViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Anggota
import com.example.finalproject092.repository.AnggotaRepository
import com.example.finalproject092.ui.navigation.DetailAnggotaDestination
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailMemUiState {
    data class Success(val anggota: Anggota) : DetailMemUiState()
    object Error : DetailMemUiState()
    object Loading : DetailMemUiState()
}

// ViewModel untuk mengelola data dan status UI pada DetailMhs
class DetailAnggotaViewModel(
    savedStateHandle: SavedStateHandle,
    private val anggotaRepo: AnggotaRepository
) : ViewModel() {

    // Mendapatkan NIM dari `SavedStateHandle`. Jika tidak ada, akan memunculkan exception.
    private val idAnggota: String = checkNotNull(savedStateHandle[DetailAnggotaDestination.idMem])
    var detailMemUiState: DetailMemUiState by mutableStateOf(DetailMemUiState.Loading)
        private set

    init {
        getMemberbyId()
    }

    // Fungsi untuk mengambil data mahasiswa berdasarkan NIM
    fun getMemberbyId() {
        viewModelScope.launch {
            detailMemUiState = DetailMemUiState.Loading
            detailMemUiState = try {
                DetailMemUiState.Success(anggotaRepo.getAnggotabyId(idAnggota))
            } catch (e: Exception) {
                DetailMemUiState.Error
            }
        }

    }

    fun deleteAnggota(idAnggota: String){
        viewModelScope.launch {
            try {
                anggotaRepo.deleteAnggota(idAnggota)
            } catch (e: IOException) {
                MembersUiState.Error
            } catch (e: HttpException) {
                MembersUiState.Error
            }
        }
    }

}