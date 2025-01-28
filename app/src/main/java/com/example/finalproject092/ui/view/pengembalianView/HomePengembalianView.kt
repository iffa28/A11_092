package com.example.finalproject092.ui.view.pengembalianView

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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.finalproject092.R
import com.example.finalproject092.model.Pengembalian
import com.example.finalproject092.ui.topAppBar.HomeEntitasTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.HomePengembalianViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.ReturnUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeReturnedBookView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailPgClick: (Int) -> Unit = {},
    viewModel: HomePengembalianViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeEntitasTopBar(
                navigateUp = navigateBack,
                title = "Pengembalian",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getReturnBookData()
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Return the Book")
            }
        },
    ) { innerPadding ->
        HomeBookStatus(
            returnUiState = viewModel.returnBookUiState,
            retryAction = {viewModel.getReturnBookData()},
            modifier = Modifier
                .padding(innerPadding),
            onDetailClick = onDetailPgClick,
            navigateToItemEntry = navigateToItemEntry
        )
    }
}



@Composable
fun HomeBookStatus(
    returnUiState: ReturnUiState,
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
        when (returnUiState) {
            is ReturnUiState.Loading -> OnLoading(modifier = Modifier.fillMaxWidth())

            is ReturnUiState.Success ->
                if (returnUiState.pengembalian.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Tidak Ada Data Pengembalian")
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = navigateToItemEntry){
                                Text(text = "Kembalikan Buku")
                            }

                        }
                    }
                } else {
                    PengembalianLayout(
                        pengembalian = returnUiState.pengembalian,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        onDetailClick = {
                            onDetailClick(it.idReturn)
                        },
                        onAddClick = navigateToItemEntry

                    )
                }

            is ReturnUiState.Error -> OnError(
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
fun PengembalianLayout(
    pengembalian: List<Pengembalian>,
    onDetailClick: (Pengembalian) -> Unit,
    onAddClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchPengembalian by remember { mutableStateOf("") }

    val filteredPengembalian = pengembalian.filter {
        it.tglDikembalikan.toString().contains(searchPengembalian, ignoreCase = true)
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
                pengembalian = searchPengembalian,
                onQueryChange = { searchPengembalian = it }, // Update query pencarian
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 10.dp)
            )
        }
        item() {
            PengembalianTable(pengembalian = filteredPengembalian,
                onDetailClick = onDetailClick,
                onAddClick = onAddClick,)
        }

    }

}

@Composable
fun SearchBar(
    pengembalian: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = pengembalian,
        onValueChange = onQueryChange,
        placeholder = { Text("Cari Judul Buku...") },
        modifier = modifier.border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp)),
        singleLine = true,
    )
    Spacer(modifier = Modifier.padding(10.dp))

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PengembalianTable(
    pengembalian: List<Pengembalian>,
    onDetailClick: (Pengembalian) -> Unit,
    onAddClick: () -> Unit = {},
    modifier: Modifier = Modifier

) {
    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(text = "Daftar Pengembalian",
            style = TextStyle(
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(onClick = onAddClick){
            Text(text = "Kembalikan Buku")
        }

    }
    Spacer(modifier = Modifier.padding(10.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)  // Menambahkan padding sekitar kolom
    ) {
        // Header Tabel
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "ID Pengembalian",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp), // Menambahkan padding antar kolom
                textAlign = TextAlign.Center
            )
            Text(
                text = "Nama Anggota",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Judul Buku",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Dikembalikan",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = "Denda",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center
            )
        }

        pengembalian.forEach { pg ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = pg.idReturn.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = pg.nama,
                    modifier = Modifier
                        .weight(1.3f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true)
                        ) {
                            onDetailClick(pg)
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
                    text = pg.judul,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = pg.tglDikembalikan.toString(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "Rp${pg.denda}",
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
