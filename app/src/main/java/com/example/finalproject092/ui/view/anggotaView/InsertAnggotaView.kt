package com.example.finalproject092.ui.view.anggotaView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.R
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.viewModel.anggotaViewModel.InsertAnggotaViewModel
import com.example.finalproject092.ui.viewModel.anggotaViewModel.InsertMembersUiEvent
import com.example.finalproject092.ui.viewModel.anggotaViewModel.InsertMembersUiState
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryMemberScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = "Tambah Anggota",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertMembersUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertMembersState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertMember()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    insertMembersUiState: InsertMembersUiState,
    onSiswaValueChange: (InsertMembersUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
){
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
            .padding(start=8.dp, end=8.dp)
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
            FormInput(
                insertMembersUiEvent = insertMembersUiState.insertMembersUiEvent,
                onValueChange = onSiswaValueChange,
                onSaveClick = onSaveClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertMembersUiEvent: InsertMembersUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertMembersUiEvent) -> Unit = {},
    onSaveClick: () -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier.padding(5.dp),
        verticalArrangement = Arrangement.spacedBy(30.dp)
    ) {
        OutlinedTextField(
            value = insertMembersUiEvent.nama,
            onValueChange = { onValueChange(insertMembersUiEvent.copy(nama = it)) },
            label = { Text(text = "Nama") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
        )
        OutlinedTextField(
            value = insertMembersUiEvent.idAnggota,
            onValueChange = { onValueChange(insertMembersUiEvent.copy(idAnggota = it)) },
            label = { Text(text = "ID Anggota") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )
        OutlinedTextField(
            value = insertMembersUiEvent.email,
            onValueChange = { onValueChange(insertMembersUiEvent.copy(email = it)) },
            label = { Text(text = "Email") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

        OutlinedTextField(
            value = insertMembersUiEvent.nomorTelepon,
            onValueChange = { onValueChange(insertMembersUiEvent.copy(nomorTelepon = it)) },
            label = { Text(text = "Nomor Telepon") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true
        )

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