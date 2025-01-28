package com.example.finalproject092.ui.view.bookView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.R
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.viewModel.bookViewModel.InsertBookUiEvent
import com.example.finalproject092.ui.viewModel.bookViewModel.InsertBookUiState
import com.example.finalproject092.ui.viewModel.bookViewModel.InsertBookViewModel
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryBookScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertBookViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = "Insert Book",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            insertBookUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertBukuState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.validateAndInsertBook()
                    if (viewModel.uiState.validationErrors.isEmpty()) {
                        navigateBack() // Only navigate if no validation errors
                    }
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
    insertBookUiState: InsertBookUiState,
    onSiswaValueChange: (InsertBookUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
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
            verticalArrangement = Arrangement.spacedBy(20.dp),
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
                insertBookUiEvent = insertBookUiState.insertBookUiEvent,
                onValueChange = onSiswaValueChange,
                onSaveClick = onSaveClick,
                validationErrors = insertBookUiState.validationErrors,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    insertBookUiEvent: InsertBookUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (InsertBookUiEvent) -> Unit = {},
    onSaveClick: () -> Unit,
    enabled: Boolean = true,
    validationErrors: List<String> = emptyList()
) {
    var expandedStatus by remember { mutableStateOf(false) }
    var expandedKategori by remember { mutableStateOf(false) }
    val statusOptions = listOf("Tersedia", "Dipinjam")
    val kategoriOptions = listOf("Fiksi", "Non-Fiksi", "Sains", "Biografi", "Antologi", "Ensiklopedia")

    Column(
        modifier = modifier.padding(5.dp),
    ) {
        // Judul Buku
        OutlinedTextField(
            value = insertBookUiEvent.judul,
            onValueChange = { onValueChange(insertBookUiEvent.copy(judul = it)) },
            label = { Text(text = "Judul Buku") },
            placeholder = { Text(text = "Masukkan judul buku") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = validationErrors.contains("Judul buku tidak boleh kosong")
        )
        Spacer(modifier = Modifier.padding(3.dp))
        // Error message for Judul Buku
        if (validationErrors.contains("Judul buku tidak boleh kosong")) {
            Text(
                text = "*Judul buku tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))

        // Penulis
        OutlinedTextField(
            value = insertBookUiEvent.penulis,
            onValueChange = { onValueChange(insertBookUiEvent.copy(penulis = it)) },
            label = { Text(text = "Penulis") },
            placeholder = { Text(text = "Masukkan nama penulis") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            isError = validationErrors.contains("Penulis tidak boleh kosong")
        )
        Spacer(modifier = Modifier.padding(3.dp))
        // Error message for Penulis
        if (validationErrors.contains("Penulis tidak boleh kosong")) {
            Text(
                text = "*Penulis tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))

        // Kategori Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedKategori,
            onExpandedChange = { expandedKategori = it }
        ) {
            OutlinedTextField(
                value = insertBookUiEvent.kategori,
                onValueChange = {},
                label = { Text(text = "Kategori") },
                placeholder = { Text(text = "Pilih kategori") },
                readOnly = true,
                enabled = enabled,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                isError = validationErrors.contains("Kategori tidak boleh kosong")
            )
            ExposedDropdownMenu(
                expanded = expandedKategori,
                onDismissRequest = { expandedKategori = false }
            ) {
                kategoriOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(insertBookUiEvent.copy(kategori = option))
                            expandedKategori = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(3.dp))
        // Error message for Kategori
        if (validationErrors.contains("Kategori tidak boleh kosong")) {
            Text(
                text = "*Kategori tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))

        // Status Dropdown
        ExposedDropdownMenuBox(
            expanded = expandedStatus,
            onExpandedChange = { expandedStatus = it }
        ) {
            OutlinedTextField(
                value = insertBookUiEvent.status,
                onValueChange = {},
                label = { Text(text = "Status") },
                placeholder = { Text(text = "Pilih status") },
                readOnly = true,
                enabled = enabled,
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                trailingIcon = {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                },
                isError = validationErrors.contains("Status tidak boleh kosong")
            )
            ExposedDropdownMenu(
                expanded = expandedStatus,
                onDismissRequest = { expandedStatus = false }
            ) {
                statusOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onValueChange(insertBookUiEvent.copy(status = option))
                            expandedStatus = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(3.dp))
        // Error message for Status
        if (validationErrors.contains("Status tidak boleh kosong")) {
            Text(
                text = "*Status tidak boleh kosong",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.padding(5.dp))

        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                modifier = Modifier.padding(12.dp),
            )
        }

        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )

        // Save button
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

