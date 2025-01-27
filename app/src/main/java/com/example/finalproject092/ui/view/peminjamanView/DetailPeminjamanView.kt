package com.example.finalproject092.ui.view.peminjamanView

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
import com.example.finalproject092.model.Peminjaman
import com.example.finalproject092.ui.topAppBar.HomeEntitasTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.DetailPeminjamanViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.DetailPjUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPeminjamanScreen(
    navigateBack: () -> Unit,
    onEditPjClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailPeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeEntitasTopBar(
                title = "Detail Peminjaman",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getPeminjamanbyId()
                }
            )
        },
    )
    { innerPadding ->
        DetailStatus(
            pjUiState = detailViewModel.detailpjUiState,
            retryAction = {detailViewModel.getPeminjamanbyId()},
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                detailViewModel.deletePeminjaman(it.idPeminjaman)
                navigateBack()
            },
            onEditClick = onEditPjClick

        )
    }
}

@Composable
fun DetailStatus(
    pjUiState: DetailPjUiState,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Peminjaman) -> Unit = {},
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
        when(pjUiState){
            is DetailPjUiState.Success -> {
                DetailPjLayout(
                    peminjaman = pjUiState.peminjaman,
                    onDeleteClick = {onDeleteClick(it)},
                    onEditClick = {onEditClick(it)},
                    modifier = modifier
                )
            }
            is DetailPjUiState.Loading -> OnLoading(modifier = modifier)
            is DetailPjUiState.Error -> OnError(retryAction, modifier = modifier)
        }

    }

}

@Composable
fun DetailPjLayout(
    peminjaman: Peminjaman,
    onDeleteClick: (Peminjaman) -> Unit,
    onEditClick: (Int) -> Unit = {},
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
            ComponentDetailPj(judul = "ID Peminjaman", isinya = peminjaman.idPeminjaman.toString())
            ComponentDetailPj(judul = "ID Buku", isinya = peminjaman.idBuku)
            ComponentDetailPj(judul = "Judul Buku", isinya = peminjaman.judul)
            ComponentDetailPj(judul = "ID Anggota", isinya = peminjaman.idAnggota)
            ComponentDetailPj(judul = "Nama Angggota", isinya = peminjaman.nama)
            ComponentDetailPj(judul = "Tanggal Peminjaman", isinya = peminjaman.tanggalPeminjaman.toString())
            ComponentDetailPj(judul = "Tanggal Peminjaman", isinya = peminjaman.tanggalPengembalian.toString())
            ComponentDetailPj(judul = "Status", isinya = peminjaman.status)
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onEditClick(peminjaman.idPeminjaman) }) {
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
                onDeleteClick(peminjaman)
            },
            onDeleteCancel = { deleteConfirmationRequired = false },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ComponentDetailPj(
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