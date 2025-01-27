package com.example.finalproject092.ui.view.peminjamanView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.R
import com.example.finalproject092.model.Peminjaman
import com.example.finalproject092.ui.topAppBar.HomeEntitasTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.BorrowUiState
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.HomePeminjamanViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBorrowView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailPjClick: (Int) -> Unit = {},
    viewModel: HomePeminjamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    // Auto-refresh interval in milliseconds
    val refreshInterval = 10_000L // 10 seconds

    LaunchedEffect(Unit) {
        viewModel.getBorrowBook()
        kotlinx.coroutines.delay(refreshInterval)
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeEntitasTopBar(
                navigateUp = navigateBack,
                title = "Peminjaman",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getBorrowBook()
                },
            )
        }
    ) { innerPadding ->
        HomeBookStatus(
            pinjamUiState = viewModel.pinjamUiState,
            retryAction = {viewModel.getBorrowBook()},
            modifier = Modifier
                .padding(innerPadding),
            onDetailClick = onDetailPjClick,
            navigateToItemEntry = navigateToItemEntry
        )
    }
}



@Composable
fun HomeBookStatus(
    pinjamUiState: BorrowUiState,
    retryAction: () -> Unit,
    onDetailClick: (Int) -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.main),

                )
    ) {
        when (pinjamUiState) {
            is BorrowUiState.Loading -> OnLoading(modifier = Modifier.fillMaxWidth())

            is BorrowUiState.Success ->
                if (pinjamUiState.peminjaman.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Tidak ada data peminjaman")
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = navigateToItemEntry){
                                Text(text = "Tambah Peminjaman")
                            }

                        }

                    }
                } else {
                    BookLayout(
                        peminjaman = pinjamUiState.peminjaman,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        onDetailClick = {
                            onDetailClick(it.idPeminjaman)
                        },
                        onAddClick = {
                            navigateToItemEntry()
                        }
                    )
                }

            is BorrowUiState.Error -> OnError(
                retryAction = retryAction,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun OnLoading(
    modifier: Modifier = Modifier
){
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = modifier.size(200.dp),
            painter = painterResource(R.drawable.loading),
            contentDescription = stringResource(R.string.loading)
        )
    }


}

@Composable
fun OnError(
    retryAction:() -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(color = Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.offline), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed),  modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookLayout(
    peminjaman: List<Peminjaman>,
    onDetailClick: (Peminjaman) -> Unit,
    onAddClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var searchPeminjaman by remember { mutableStateOf("") }

    val filteredPeminjaman = peminjaman.filter {
        it.idAnggota.contains(searchPeminjaman, ignoreCase = true)
    }

    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.white),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
            ),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SearchBar(
                peminjaman = searchPeminjaman,
                onQueryChange = {
                    searchPeminjaman = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 10.dp)
            )
        }
        item(filteredPeminjaman) {
            PeminjamanTable(peminjaman = filteredPeminjaman,
                onAddClick = onAddClick,
                onDetailClick = onDetailClick)
        }

    }

}

@Composable
fun SearchBar(
    peminjaman: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = peminjaman,
        onValueChange = onQueryChange,
        placeholder = { Text("Cari...") },
        modifier = modifier.border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)),
    )
    Spacer(modifier = Modifier.width(8.dp))

}


@Composable
fun PeminjamanTable(
    peminjaman: List<Peminjaman>,
    onDetailClick: (Peminjaman) -> Unit,
    onAddClick: () -> Unit,
) {
    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Daftar Peminjaman",
            style = TextStyle(
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onAddClick){
            Text(text = "Tambah Peminjaman")
        }

    }
    Spacer(modifier = Modifier.padding(10.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp))
    ) {
        // Header Tabel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(id = R.color.warnatabel), shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(vertical = 6.dp)
        ) {
            Text(
                text = "ID",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )

            Text(
                text = "Judul",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.3f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "Anggota",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.8f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "Tanggal Peminjaman",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "Tanggal Pengembalian",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "Status",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
        }
        peminjaman.forEach { pj ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pj.idPeminjaman.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 14.sp)

                )
                Text(
                    text = pj.judul,
                    modifier = Modifier
                        .weight(1.3f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true)
                        ) {
                            onDetailClick(pj)
                        }
                        .padding(horizontal = 4.dp),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = pj.nama,
                    modifier = Modifier
                        .weight(1.8f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp)
                )
                Text(
                    text = pj.tanggalPeminjaman.toString(),
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp)
                )
                Text(
                    text = pj.tanggalPengembalian.toString(),
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(horizontal = 5.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = pj.status,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(horizontal = 5.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}