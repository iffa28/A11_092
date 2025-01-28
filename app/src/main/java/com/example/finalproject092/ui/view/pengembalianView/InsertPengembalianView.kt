package com.example.finalproject092.ui.view.pengembalianView

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
import com.example.finalproject092.model.Peminjaman
import com.example.finalproject092.ui.topAppBar.HomeEntitasTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.InsertPengembalianViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.InsertPgUiEvent
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.InsertPgUiState
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryReturnScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeEntitasTopBar(
                title = "Pengembalian Buku",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertPgUiState = viewModel.uiState,
            onPgValueChange = viewModel::updateInsertPengembalianState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertPengembalian()
                    navigateBack()
                }
            },
            peminjamanList = viewModel.peminjamanList,
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
    insertPgUiState: InsertPgUiState,
    onPgValueChange: (InsertPgUiEvent) -> Unit,
    peminjamanList: List<Peminjaman>,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expandedTransaksi by remember { mutableStateOf(false) }
    var selectedTransaksi by rememberSaveable { mutableStateOf(insertPgUiState.insertPgUiEvent.idAnggota ?: "") }

    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(insertPgUiState.insertPgUiEvent.idPeminjaman) {
        val peminjaman = peminjamanList.find { it.idPeminjaman == insertPgUiState.insertPgUiEvent.idPeminjaman }
        if (peminjaman != null) {
            selectedTransaksi = peminjaman.idAnggota
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
            ExposedDropdownMenuBox(
                expanded = expandedTransaksi,
                onExpandedChange = { expandedTransaksi = it }
            ) {
                OutlinedTextField(
                    value = selectedTransaksi,
                    onValueChange = {},
                    label = { Text(text = "Pilih Transaksi Peminjaman") },
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
                    expanded = expandedTransaksi,
                    onDismissRequest = { expandedTransaksi = false }
                ) {
                    peminjamanList.forEach { mem ->
                        DropdownMenuItem(
                            text = { Text(mem.idAnggota) },
                            onClick = {
                                selectedTransaksi = mem.idAnggota
                                onPgValueChange(insertPgUiState.insertPgUiEvent.copy(idPeminjaman = mem.idPeminjaman)) // Simpan idPeminjaman
                                expandedTransaksi = false // Tutup dropdown setelah memilih
                            }
                        )
                    }
                }
            }

            // Input untuk memilih tanggal dikembalikan
            OutlinedTextField(
                value = insertPgUiState.insertPgUiEvent.tglDikembalikan?.toString() ?: "",
                onValueChange = {},
                label = { Text(text = "Tanggal Dikembalikan") },
                placeholder = { Text(text = "Pilih Tanggal Dikembalikan") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        showDatePicker = true
                    },
                enabled = false, // Disable manual input
                singleLine = true
            )

            if (showDatePicker) {
                showDatePickerDialog { selectedDate ->
                    onPgValueChange(insertPgUiState.insertPgUiEvent.copy(tglDikembalikan = selectedDate)) // Perbarui tanggal
                    showDatePicker = false
                }
            }

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
fun showDatePickerDialog(
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
