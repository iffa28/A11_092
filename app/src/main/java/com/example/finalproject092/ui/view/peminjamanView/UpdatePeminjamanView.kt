package com.example.finalproject092.ui.view.peminjamanView

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
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