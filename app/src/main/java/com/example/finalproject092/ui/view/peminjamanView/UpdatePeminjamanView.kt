package com.example.finalproject092.ui.view.peminjamanView

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.InsertPeminjamanViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.UpdatePeminjamanViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePeminjamanScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdatePeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(updateViewModel.updatePjUiState.insertPjUiEvent.idAnggota) {
        updateViewModel.updateInsertPjState(updateViewModel.updatePjUiState.insertPjUiEvent)
        Log.d("UpdatePeminjamanScreen", "Memuat data pengembalian untuk IdAnggota: ${updateViewModel.updatePjUiState.insertPjUiEvent.idAnggota}")
    }

    LaunchedEffect(updateViewModel.updatePjUiState.insertPjUiEvent.idBuku) {
        updateViewModel.updateInsertPjState(updateViewModel.updatePjUiState.insertPjUiEvent)
        Log.d("UpdatePeminjamanScreen", "Memuat data pengembalian untuk IdAnggota: ${updateViewModel.updatePjUiState.insertPjUiEvent.idBuku}")
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Edit Peminjaman",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryBody(
            insertPjUiState = updateViewModel.updatePjUiState,
            onPjValueChange = updateViewModel::updateInsertPjState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updatePeminjamanData()
                    onNavigateUp()
                }
            },
            bukuList = updateViewModel.bukuList,
            anggotaList = updateViewModel.anggotaList,
            modifier = Modifier.padding(innerPadding),

        )

    }
}