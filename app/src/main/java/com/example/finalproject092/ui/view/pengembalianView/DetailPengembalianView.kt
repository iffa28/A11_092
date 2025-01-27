package com.example.finalproject092.ui.view.pengembalianView

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
import com.example.finalproject092.model.Pengembalian
import com.example.finalproject092.ui.topAppBar.HomeEntitasTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.DetailPengembalianViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.DetailPgUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailPengembalianScreen(
    navigateBack: () -> Unit,
    onEditPgClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailPengembalianViewModel= viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeEntitasTopBar(
                title = "Detail Pengembalian",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack,
                onRefresh = {
                    detailViewModel.getPengembalianbyId()
                }
            )
        },
    )
    { innerPadding ->
        DetailStatus(
            pgUiState = detailViewModel.detailPgUiState,
            retryAction = {detailViewModel.getPengembalianbyId()},
            modifier = Modifier.padding(innerPadding),
            onDeleteClick = {
                detailViewModel.deletePengembalian(it.idReturn)
                navigateBack()
            },
            onEditClick = onEditPgClick

        )
    }
}

@Composable
fun DetailStatus(
    pgUiState: DetailPgUiState,
    onEditClick: (Int) -> Unit,
    onDeleteClick: (Pengembalian) -> Unit = {},
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
        when(pgUiState){
            is DetailPgUiState.Success -> {
                DetailPjLayout(
                    pengembalian = pgUiState.pengembalian,
                    onDeleteClick = {onDeleteClick(it)},
                    onEditClick = {onEditClick(it)},
                    modifier = modifier
                )
            }
            is DetailPgUiState.Loading -> OnLoading(modifier = modifier)
            is DetailPgUiState.Error -> OnError(retryAction, modifier = modifier)
        }

    }

}

@Composable
fun DetailPjLayout(
    pengembalian: Pengembalian,
    onDeleteClick: (Pengembalian) -> Unit,
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
            ComponentDetailPg(judul = "ID pengembalian", isinya = pengembalian.idReturn.toString())
            ComponentDetailPg(judul = "ID Peminjaman", isinya = pengembalian.idPeminjaman.toString())
            ComponentDetailPg(judul = "Judul Buku", isinya = pengembalian.judul)
            ComponentDetailPg(judul = "Nama Anggota", isinya = pengembalian.nama)
            ComponentDetailPg(judul = "Tanggal DiKembalikan", isinya = pengembalian.tglDikembalikan.toString())
            ComponentDetailPg(judul = "Denda", isinya = pengembalian.denda.toString())
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onEditClick(pengembalian.idReturn) }) {
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
                onDeleteClick(pengembalian)
            },
            onDeleteCancel = { deleteConfirmationRequired = false },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun ComponentDetailPg(
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