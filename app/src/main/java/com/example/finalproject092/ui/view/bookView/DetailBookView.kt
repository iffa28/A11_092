package com.example.finalproject092.ui.view.bookView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.R
import com.example.finalproject092.model.Buku
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.topAppBar.HomeEntitasTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.bookViewModel.DetailBookUiState
import com.example.finalproject092.ui.viewModel.bookViewModel.DetailBookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBookScreen(
    navigateBack: () -> Unit,
    onEditBukuClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailBookViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeEntitasTopBar(
                title = "Detail Buku",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getBookbyId()
                }
            )
        },
    )
    { innerPadding ->
        DetailStatus(
            bookUiState = detailViewModel.detailBookUiState,
            retryAction = {detailViewModel.getBookbyId() },
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                detailViewModel.deleteBook(it.idBuku)
                navigateBack()
            },
            onEditClick = onEditBukuClick

        )
    }
}

@Composable
fun DetailStatus(
    bookUiState: DetailBookUiState,
    onEditClick: (String) -> Unit,
    onDeleteClick: (Buku) -> Unit = {},
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.main),
                        colorResource(id = R.color.contain)
                    )
                ),
            )
    ) {
        when(bookUiState){
            is DetailBookUiState.Success -> {
                DetailBookLayout(
                    buku = bookUiState.buku,
                    onDeleteClick = {onDeleteClick(it)},
                    onEditBkClick = {onEditClick(it)},
                    modifier = modifier
                )
            }
            is DetailBookUiState.Loading -> OnLoading(modifier = modifier)
            is DetailBookUiState.Error -> OnError(retryAction, modifier = modifier)
        }

    }

}

@Composable
fun DetailBookLayout(
    buku: Buku,
    onDeleteClick: (Buku) -> Unit,
    onEditBkClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(19.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(id = R.color.white),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            ComponentDetailBook(judul = "ID Buku", isinya = buku.idBuku)
            ComponentDetailBook(judul = "Judul Buku", isinya = buku.judul)
            ComponentDetailBook(judul = "Penulis", isinya = buku.penulis)
            ComponentDetailBook(judul = "Kategori", isinya = buku.kategori)
            ComponentDetailBook(judul = "Status", isinya = buku.status)
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onEditBkClick(buku.idBuku) }) {
                Icon(
                    painter = painterResource(id = R.drawable.edit),
                    contentDescription = "Edit",
                    tint = colorResource(id = R.color.HomeTil),
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(onClick = {
                deleteConfirmationRequired = true
            }){
                Icon(
                    painter = painterResource(id = R.drawable.delete),
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
    if (deleteConfirmationRequired) {
        DeleteConfirmationDialog(
            onDeleteConfirm = {
                deleteConfirmationRequired = false
                onDeleteClick(buku)
            },
            onDeleteCancel = { deleteConfirmationRequired = false },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ComponentDetailBook(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp)
    ) {
        Text(
            text = "$judul:",
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.HomeTil)
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.HomeTil)
        )
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { /* Do Nothing */ },
        title = { Text("Delete Data", fontWeight = FontWeight.Bold) },
        text = { Text("Apakah anda yakin ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Yes")
            }
        }
    )
}
