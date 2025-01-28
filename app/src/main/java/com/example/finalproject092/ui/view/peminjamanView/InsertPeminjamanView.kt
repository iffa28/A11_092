package com.example.finalproject092.ui.view.peminjamanView

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.R
import com.example.finalproject092.model.Anggota
import com.example.finalproject092.model.Buku
import com.example.finalproject092.ui.topAppBar.HomeEntitasTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.InsertPeminjamanViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.InsertPjUiEvent
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.InsertPjUiState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBorrowScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeEntitasTopBar(
                title = "Tambah Peminjaman",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertPjUiState = viewModel.uiState,
            onPjValueChange = viewModel::updateInsertPeminjamanState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPeminjaman()
                    navigateBack()
                }
            },
            bukuList = viewModel.bukuList,
            anggotaList = viewModel.anggotaList,
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBody(
    insertPjUiState: InsertPjUiState,
    onPjValueChange: (InsertPjUiEvent) -> Unit,
    bukuList: List<Buku>,
    anggotaList: List<Anggota>,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedBuku by remember { mutableStateOf(false) }
    var selectedJudul by rememberSaveable { mutableStateOf(insertPjUiState.insertPjUiEvent.judul ?: "") }

    var expandedAnggota by remember { mutableStateOf(false) }
    var selectedNama by remember { mutableStateOf(insertPjUiState.insertPjUiEvent.nama ?: "") }

    var showPeminjamanDatePicker by remember { mutableStateOf(false) }
    var showPengembalianDatePicker by remember { mutableStateOf(false) }

    // Mengambil idPeminjaman yang sesuai dengan idAnggota
    LaunchedEffect(insertPjUiState.insertPjUiEvent.idAnggota) {
        val anggota = anggotaList.find { it.idAnggota == insertPjUiState.insertPjUiEvent.idAnggota }
        if (anggota != null) {
            selectedNama = anggota.nama
        }
    }

    LaunchedEffect(insertPjUiState.insertPjUiEvent.idBuku) {
        val  buku = bukuList.find { it.idBuku == insertPjUiState.insertPjUiEvent.idBuku }
        if (buku != null) {
            selectedJudul = buku.judul
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.main),
                        colorResource(id = R.color.contain)
                    )
                )
            )
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(10.dp)
                .background(
                    color = colorResource(id = R.color.contain),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {


            // Dropdown untuk memilih nama anggota
            ExposedDropdownMenuBox(
                expanded = expandedAnggota,
                onExpandedChange = { expandedAnggota = it }
            ) {
                OutlinedTextField(
                    value = selectedNama,
                    onValueChange = {},
                    label = { Text(text = "Pilih Nama Anggota") },
                    readOnly = true,
                    enabled = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                )
                ExposedDropdownMenu(
                    expanded = expandedAnggota,
                    onDismissRequest = { expandedAnggota = false }
                ) {
                    anggotaList.forEach { mem ->
                        DropdownMenuItem(
                            text = { Text(mem.nama) },
                            onClick = {
                                selectedNama = mem.nama
                                onPjValueChange(insertPjUiState.insertPjUiEvent.copy(idAnggota = mem.idAnggota))
                                expandedAnggota = false
                            }
                        )
                    }
                }
            }

            // Dropdown untuk memilih buku
            ExposedDropdownMenuBox(
                expanded = expandedBuku,
                onExpandedChange = { expandedBuku = it }
            ) {
                OutlinedTextField(
                    value = selectedJudul,
                    onValueChange = {},
                    label = { Text(text = "Pilih Buku") },
                    readOnly = true,
                    enabled = true,
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    trailingIcon = {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                )
                ExposedDropdownMenu(
                    expanded = expandedBuku,
                    onDismissRequest = { expandedBuku = false }
                ) {
                    bukuList.forEach { buku ->
                        val isDipinjam = buku.status == "Dipinjam"
                        DropdownMenuItem(
                            text = { Text(buku.judul) },
                            onClick = {
                                if (!isDipinjam) {
                                    selectedJudul = buku.judul
                                    onPjValueChange(insertPjUiState.insertPjUiEvent.copy(idBuku = buku.idBuku))
                                    expandedBuku = false
                                }
                            },
                            enabled = !isDipinjam
                        )
                    }
                }
            }

            // Input untuk Tanggal Peminjaman
            OutlinedTextField(
                value = insertPjUiState.insertPjUiEvent.tanggalPeminjaman?.toString() ?: "",
                onValueChange = {},
                label = { Text(text = "Tanggal Peminjaman") },
                placeholder = { Text(text = "Pilih Tanggal Peminjaman") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showPeminjamanDatePicker = true
                    },
                enabled = false,
                singleLine = true
            )
            if (showPeminjamanDatePicker) {
                showDatePjPickerDialog { selectedDate ->
                    onPjValueChange(insertPjUiState.insertPjUiEvent.copy(tanggalPeminjaman = selectedDate))
                    showPeminjamanDatePicker = false
                }
            }

            // Input untuk Tanggal Pengembalian
            OutlinedTextField(
                value = insertPjUiState.insertPjUiEvent.tanggalPengembalian?.toString() ?: "",
                onValueChange = {},
                label = { Text(text = "Tanggal Pengembalian") },
                placeholder = { Text(text = "Pilih Tanggal Pengembalian") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showPengembalianDatePicker = true
                    },
                enabled = false,
                singleLine = true
            )
            if (showPengembalianDatePicker) {
                showDatePjPickerDialog { selectedDate ->
                    onPjValueChange(insertPjUiState.insertPjUiEvent.copy(tanggalPengembalian = selectedDate))
                    showPengembalianDatePicker = false
                }
            }

            // Tombol Simpan
            Button(
                onClick = onSaveClick,
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = R.color.HomeTil),
                    contentColor = colorResource(id = R.color.white)
                ),
                elevation = ButtonDefaults.elevatedButtonElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Text(text = "Simpan", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}

@Composable
fun showDatePjPickerDialog(
    onDateSelected: (LocalDate) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}




