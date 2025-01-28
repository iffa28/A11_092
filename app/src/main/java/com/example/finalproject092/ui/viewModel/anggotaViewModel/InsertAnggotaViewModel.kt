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

    suspend fun validateAndInsertAnggota() {
        val validationResult = validateInput(uiState.insertMembersUiEvent)

        if (validationResult.isValid) {
            viewModelScope.launch {
                try {
                    anggotaRepo.insertAnggota(uiState.insertMembersUiEvent.toMem())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        } else {
            // Update state untuk menampilkan pesan kesalahan
            uiState = uiState.copy(validationErrors = validationResult.errors)
        }
    }

    private fun validateInput(event: InsertMembersUiEvent): ValidationResult {
        val errors = mutableListOf<String>()

        if (event.nama.isEmpty()) errors.add("Nama tidak boleh kosong")
        if (event.idAnggota.isEmpty()) errors.add("ID Anggota tidak boleh kosong")
        if (event.email.isEmpty()) errors.add("Email tidak boleh kosong")
        if (!event.email.contains("@")) errors.add("Email tidak valid")
        if (event.nomorTelepon.isEmpty()) errors.add("Nomor telepon tidak boleh kosong")

        return ValidationResult(errors.isEmpty(), errors)
    }

}
data class ValidationResult(val isValid: Boolean, val errors: List<String>)

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
    val insertMembersUiEvent: InsertMembersUiEvent = InsertMembersUiEvent(),
    val validationErrors: List<String> = emptyList()
)

data class InsertMembersUiEvent(
    val idAnggota: String = "",
    val nama: String = "",
    val email: String = "",
    val nomorTelepon: String = "",
    val isValid: Boolean = true
)