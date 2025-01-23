package com.example.finalproject092.ui.viewModel.anggotaViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Anggota
import com.example.finalproject092.repository.AnggotaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class MembersUiState{
    data class Success(val anggota: List<Anggota>) : MembersUiState()
    object Error : MembersUiState()
    object Loading : MembersUiState()
}

class HomeAnggotaViewModel(
    private val anggotaRepository: AnggotaRepository
) : ViewModel() {
    var memUiState: MembersUiState by mutableStateOf(MembersUiState.Loading)
        private set

    init {
        getDataMembers()
    }

    fun getDataMembers() {
        viewModelScope.launch {
            memUiState = MembersUiState.Loading
            memUiState = try {
                MembersUiState.Success(anggotaRepository.getAllAnggota())
            } catch (e: IOException) {
                MembersUiState.Error
            } catch (e: HttpException) {
                MembersUiState.Error

            }
        }
    }

    fun deleteAnggota(idAnggota: String){
        viewModelScope.launch {
            try {
                anggotaRepository.deleteAnggota(idAnggota)
            } catch (e: IOException) {
                MembersUiState.Error
            } catch (e: HttpException) {
                MembersUiState.Error
            }
        }
    }

}