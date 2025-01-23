package com.example.finalproject092.ui.view.bookView

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.R
import com.example.finalproject092.model.Buku
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.viewModel.bookViewModel.BookUiState
import com.example.finalproject092.ui.viewModel.bookViewModel.HomeBookViewModel
import com.example.finalproject092.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeBookView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onEditBukuClick: (Int) -> Unit,
    onDetailBukuClick: (Int) -> Unit = {},
    viewModel: HomeBookViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
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
            onDeleteClick = {
                viewModel.deleteBook(it.idBuku)
                viewModel.getDataBook()
            },
            onEditClick = onEditBukuClick
        )
    }
}



@Composable
fun HomeBookStatus(
    bookUiState: BookUiState,
    retryAction: () -> Unit,
    onDetailClick: (Int) -> Unit,
    onDeleteClick: (Buku) -> Unit = {},
    onEditClick: (Int) -> Unit,
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
                        Text(text = "Tidak ada data Buku")
                    }
                } else {
                    BookLayout(
                        buku = bookUiState.buku,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        onDetailClick = {
                            onDetailClick(it.idBuku)
                        },
                        onDeleteClick = {
                            onDeleteClick(it)
                        },
                        onEditBkClick = {
                            onEditClick(it)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookLayout(
    buku: List<Buku>,
    onDetailClick: (Buku) -> Unit,
    onDeleteClick: (Buku) -> Unit = {},
    onEditBkClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val filteredBuku = if (searchQuery.isNotEmpty()) {
        buku.filter { it.judul.contains(searchQuery, ignoreCase = true) }
    } else {
        buku
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
                query = searchQuery,
                onQueryChange = { searchQuery = it }, // Update query pencarian
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 10.dp)
            )
        }
        items(buku){ bk ->
            BookCard(
                buku = bk,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {onDetailClick(bk)},
                onDeleteClick = {
                    onDeleteClick(bk)
                },
                onEditClick = {
                    onEditBkClick(it.idBuku)
                }
            )

        }

    }

}

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        placeholder = { Text("Cari buku...") },
        modifier = modifier.border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)),
        singleLine = true,
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
fun BookCard (
    buku: Buku,
    onDeleteClick: (Buku) -> Unit = {},
    onEditClick: (Buku) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    var deleteConfirmationRequired by remember { mutableStateOf(false) }
    Card(
        modifier = modifier.border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(18.dp)),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),

    ){
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = buku.judul,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = {onEditClick(buku)}) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Delete"
                        )
                    }

                    IconButton(onClick = {
                        deleteConfirmationRequired = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }

            }
            Text(
                text = buku.penulis,
                style = MaterialTheme.typography.titleMedium
            )
            Row() {
                Text(
                    text = buku.kategori,
                    style = MaterialTheme.typography.titleMedium
                )
                Row(modifier =Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    Text(
                        text = buku.status,
                        style = MaterialTheme.typography.titleMedium
                    )
                }



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
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit, onDeleteCancel: () -> Unit, modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do Nothing */  },
        title = { Text("Delete Data") },
        text = { Text("Apakah anda yakin ingin menghapus data?") },
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