package com.example.finalproject092.ui.viewModel.anggotaViewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Anggota
import com.example.finalproject092.repository.AnggotaRepository
import kotlinx.coroutines.launch

class InsertAnggotaViewModel(
    private val anggotaRepo : AnggotaRepository
): ViewModel() {
    var uiState by mutableStateOf(InsertMembersUiState())
        private set

    fun updateInsertMembersState(insertMembersUiEvent: InsertMembersUiEvent){
        uiState = InsertMembersUiState(insertMembersUiEvent = insertMembersUiEvent)
    }

    suspend fun insertMember(){
        viewModelScope.launch {
            try {
                anggotaRepo.insertAnggota(uiState.insertMembersUiEvent.toMem())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}

fun InsertMembersUiEvent.toMem(): Anggota = Anggota(
    idAnggota = idAnggota,
    nama = nama,
    email = email,
    nomorTelepon = nomorTelepon,
)

fun Anggota.toUiStateMembers(): InsertMembersUiState = InsertMembersUiState(
    insertMembersUiEvent = toInsertMembersUiEvent()
)

fun Anggota.toInsertMembersUiEvent(): InsertMembersUiEvent = InsertMembersUiEvent(
    idAnggota = idAnggota,
    nama = nama,
    email = email,
    nomorTelepon = nomorTelepon,
)

data class InsertMembersUiState(
    val insertMembersUiEvent: InsertMembersUiEvent = InsertMembersUiEvent()
)

data class InsertMembersUiEvent(
    val idAnggota: String = "",
    val nama: String = "",
    val email: String = "",
    val nomorTelepon: String = "",
)