package com.example.finalproject092.ui.view.anggotaView

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.anggotaViewModel.UpdateAnggotaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateAnggotaScreen(
    navigateBack: () -> Unit,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    updateViewModel: UpdateAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Edit Anggota",
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        modifier = modifier
    ) { innerPadding ->
        EntryBody(
            insertMembersUiState = updateViewModel.updateMemUiState,
            onSiswaValueChange = updateViewModel::updateInsertMemState,
            onSaveClick = {
                coroutineScope.launch {
                    updateViewModel.updateMemberData()
                    onNavigateUp()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}