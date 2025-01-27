package com.example.finalproject092.ui.view.bookView

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import com.example.finalproject092.model.Buku
import com.example.finalproject092.ui.topAppBar.HomeEntitasTopBar
import com.example.finalproject092.ui.viewModel.bookViewModel.BookUiState
import com.example.finalproject092.ui.viewModel.bookViewModel.HomeBookViewModel
import com.example.finalproject092.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBookView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailBukuClick: (String) -> Unit = {},
    viewModel: HomeBookViewModel = viewModel(factory = PenyediaViewModel.Factory)
){

    // Auto-refresh interval in milliseconds
    val refreshInterval = 10_000L // 10 seconds

    LaunchedEffect(Unit) {
            viewModel.getDataBook()
            kotlinx.coroutines.delay(refreshInterval)
    }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeEntitasTopBar(
                navigateUp = navigateBack,
                title = "Home Buku",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getDataBook()
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Book")
            }
        },
    ) { innerPadding ->
        HomeBookStatus(
            bookUiState = viewModel.bukuUiState,
            retryAction = {viewModel.getDataBook()},
            modifier = Modifier
                .padding(innerPadding),
            onDetailClick = onDetailBukuClick,
            navigateToItemEntry = navigateToItemEntry
        )
    }
}



@Composable
fun HomeBookStatus(
    bookUiState: BookUiState,
    retryAction: () -> Unit,
    navigateToItemEntry: () -> Unit,
    onDetailClick: (String) -> Unit,
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.main),

            )
    ) {
        when (bookUiState) {
            is BookUiState.Loading -> OnLoading(modifier = Modifier.fillMaxWidth())

            is BookUiState.Success ->
                if (bookUiState.buku.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Tidak ada data Buku")
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = navigateToItemEntry){
                                Text(text = "Tambah Buku")
                            }

                        }
                    }
                } else {
                    BookLayout(
                        buku = bookUiState.buku,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        onDetailClick = {
                            onDetailClick(it.idBuku)
                        }
                    )
                }

            is BookUiState.Error -> OnError(
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

@Composable
fun BookLayout(
    buku: List<Buku>,
    onDetailClick: (Buku) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchBuku by remember { mutableStateOf("") }

    val filteredBuku = buku.filter {
        it.judul.contains(searchBuku, ignoreCase = true)

    }

    LazyColumn(
        modifier = modifier
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.white),
                shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SearchBar(
                buku = searchBuku,
                onQueryChange = { searchBuku = it }, // Update query pencarian
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 10.dp)
            )
        }
        item(filteredBuku) {
            BookTable(buku = filteredBuku,
                onDetailClick = onDetailClick)
        }

    }

}

@Composable
fun SearchBar(
    buku: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.padding(6.dp))
    OutlinedTextField(
        value = buku,
        onValueChange = onQueryChange,
        placeholder = {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cari Judul Buku...",
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        shape = RoundedCornerShape(13.dp),
        modifier = Modifier
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(13.dp)),
        singleLine = true
    )
    Spacer(modifier = Modifier.padding(10.dp))

    Text(text = "Daftar Buku",
        style = TextStyle(
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp)
        )
}

@Composable
fun BookTable(
    buku: List<Buku>,
    onDetailClick: (Buku) -> Unit,
) {
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
                text = "ID Buku",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.2f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )

            Text(
                text = "Judul",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "Penulis",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "Kategori",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "Status Ketersediaan",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
        }
        buku.forEach { bk ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = bk.idBuku,
                    modifier = Modifier
                        .weight(1.3f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 14.sp)

                )
                Text(
                    text = bk.judul,
                    modifier = Modifier
                        .weight(2f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true)
                        ) {
                            onDetailClick(bk)
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
                    text = bk.penulis,
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp)
                )
                Text(
                    text = bk.kategori,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp)
                )
                Text(
                    text = bk.status,
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