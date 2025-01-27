package com.example.finalproject092.ui.viewModel.pengembalianViewModel

import com.example.finalproject092.model.Pengembalian
import com.example.finalproject092.repository.PengembalianRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalproject092.model.Peminjaman
import com.example.finalproject092.repository.PeminjamanRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class InsertPengembalianViewModel(
    private val pengembalianRepo : PengembalianRepository,
    private val peminjamanRepo : PeminjamanRepository,
): ViewModel() {
    var uiState by mutableStateOf(InsertPgUiState())
        private set

    var peminjamanList by mutableStateOf<List<Peminjaman>>(emptyList())
        private set
    var IDanggota by mutableStateOf<List<String>>(emptyList())
        private set

    init {
        fetchPeminjamanList()
    }

    private fun fetchPeminjamanList() {
        viewModelScope.launch {
            try {
                peminjamanList = peminjamanRepo.getAllPinjam()
                IDanggota = peminjamanList.map { it.idAnggota}
                uiState = uiState.copy(peminjamanList = peminjamanList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateInsertPengembalianState(insertPgUiEvent: InsertPgUiEvent){
        uiState = uiState.copy(insertPgUiEvent = insertPgUiEvent)
    }


    suspend fun insertPengembalian(){
        viewModelScope.launch {
            try {
                pengembalianRepo.insertReturned(uiState.insertPgUiEvent.toPg())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

}

fun InsertPgUiEvent.toPg(): Pengembalian {
    val validTglDikembalikan = tglDikembalikan ?: throw IllegalArgumentException("Tanggal dikembalikan harus diisi.")
    return Pengembalian(
        idReturn = idReturn,
        idPeminjaman = idPeminjaman,
        nama = nama,
        idAnggota = idAnggota,
        judul = judul,
        tglDikembalikan = validTglDikembalikan,
        denda = denda
    )
}

fun Pengembalian.toUiStatePg(): InsertPgUiState = InsertPgUiState(
    insertPgUiEvent = toInsertPgUiEvent()
)

fun Pengembalian.toInsertPgUiEvent(): InsertPgUiEvent = InsertPgUiEvent(
    idReturn = idReturn,
    idPeminjaman = idPeminjaman,
    nama = nama,
    judul = judul,
    idAnggota = idAnggota,
    tglDikembalikan = tglDikembalikan,
    denda = denda
)

data class InsertPgUiState(
    val peminjamanList: List<Peminjaman> = emptyList(),
    val insertPgUiEvent: InsertPgUiEvent = InsertPgUiEvent()

)

data class InsertPgUiEvent(
    val idReturn: Int = 0,
    val idPeminjaman: Int = 0,
    val nama: String = "",
    val idAnggota: String = "",
    val judul: String = "",
    val tglDikembalikan: LocalDate? = null,
    val denda: Int = 0
)