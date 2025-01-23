package com.example.finalproject092.ui.view.bookView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.model.Buku
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.bookViewModel.DetailBookUiState
import com.example.finalproject092.ui.viewModel.bookViewModel.DetailBookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBookScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailBookViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = "Detail",
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
            modifier = Modifier.padding(innerPadding)

        )
    }
}

@Composable
fun DetailStatus(
    bookUiState: DetailBookUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
){
    when(bookUiState){
        is DetailBookUiState.Success -> {
            DetailBookLayout(
                buku = bookUiState.buku,
                modifier = modifier
            )
        }
        is DetailBookUiState.Loading -> OnLoading(modifier = modifier)
        is DetailBookUiState.Error -> OnError(retryAction, modifier = modifier)
    }
}

@Composable
fun DetailBookLayout(
    buku: Buku,
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier.padding(top = 90.dp)){
        ItemDetailBook(
            buku = buku,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(10.dp))
    }

}

@Composable
fun ItemDetailBook(
    modifier: Modifier = Modifier,
    buku : Buku
)
{
    Column(modifier = Modifier.background(color = Color.LightGray)
    ) {
        ComponentDetailBook(judul = "ID Buku", isinya = buku.idBuku.toString())
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailBook(judul = "Judul Buku", isinya = buku.judul)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailBook(judul = "Penulis", isinya = buku.penulis)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailBook(judul = "Kategori", isinya = buku.kategori)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailBook(judul = "Status", isinya = buku.status)
        Spacer(modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun ComponentDetailBook(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(start = 20.dp, 10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            color = Color(0XFF3E5879),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            color = Color(0XFF213555),
            fontWeight = FontWeight.Bold,
        )

    }

}