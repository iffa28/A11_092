package com.example.finalproject092.ui.viewModel.anggotaViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.repository.AnggotaRepository
import com.example.finalproject092.ui.navigation.UpdateAnggotaDestination
import kotlinx.coroutines.launch

class UpdateAnggotaViewModel(
    savedStateHandle: SavedStateHandle,
    private val anggotaRepo : AnggotaRepository
) : ViewModel() {
    var updateMemUiState by mutableStateOf(InsertMembersUiState())
        private set
    val idAnggota: String = checkNotNull(savedStateHandle[UpdateAnggotaDestination.idMem])

    init {
        viewModelScope.launch {
            updateMemUiState = anggotaRepo.getAnggotabyId(idAnggota).toUiStateMembers()
        }
    }

    fun updateInsertMemState(insertMembersUiEvent: InsertMembersUiEvent){
        updateMemUiState = InsertMembersUiState(insertMembersUiEvent = insertMembersUiEvent)
    }

    suspend fun  updateMemberData(){
        viewModelScope.launch {
            try {
                anggotaRepo.updateAnggota(idAnggota, updateMemUiState.insertMembersUiEvent.toMem())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}