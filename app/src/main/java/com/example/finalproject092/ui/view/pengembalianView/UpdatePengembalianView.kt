package com.example.finalproject092.ui.view.pengembalianView

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.UpdatePengembalianViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdatePengembalianScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdatePengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory),
) {
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Edit Pengembalian",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryBody(
            insertPgUiState = updateViewModel.updatePgUiState,
            onPgValueChange = updateViewModel::updateInsertPgState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updatePengembalianData()
                    onNavigateUp()
                }
            },
            peminjamanList = updateViewModel.peminjamanList,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
