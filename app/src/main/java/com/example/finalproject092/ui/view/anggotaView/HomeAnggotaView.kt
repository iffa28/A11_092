package com.example.finalproject092.ui.view.anggotaView

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.ripple.rememberRipple
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
import com.example.finalproject092.model.Anggota
import com.example.finalproject092.model.Buku
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.topAppBar.HomeEntitasTopBar
import com.example.finalproject092.ui.view.bookView.BookTable
import com.example.finalproject092.ui.viewModel.anggotaViewModel.HomeAnggotaViewModel
import com.example.finalproject092.ui.viewModel.anggotaViewModel.MembersUiState
import com.example.finalproject092.ui.viewModel.PenyediaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAnggotaView(
    navigateBack: () -> Unit,
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    viewModel: HomeAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val refreshInterval = 10_000L // 10 seconds

    LaunchedEffect(Unit) {
        viewModel.getDataMembers()
        kotlinx.coroutines.delay(refreshInterval)
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            HomeEntitasTopBar(
                navigateUp = navigateBack,
                title = "Home Anggota",
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getDataMembers()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Anggota")
            }
        },
    ) { innerPadding ->
        HomeAnggotaStatus(
            membersUiState = viewModel.memUiState,
            retryAction = {viewModel.getDataMembers()},
            modifier = Modifier
                .padding(innerPadding),
            onDetailClick = onDetailClick,
            navigateToItemEntry = navigateToItemEntry
        )
    }
}



@Composable
fun HomeAnggotaStatus(
    membersUiState: MembersUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    navigateToItemEntry: () -> Unit,
    onDetailClick: (String) -> Unit,
){
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = colorResource(id = R.color.main),

                )
    ) {
        when (membersUiState) {
            is MembersUiState.Loading -> OnLoading(modifier = Modifier.fillMaxWidth())

            is MembersUiState.Success ->
                if (membersUiState.anggota.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(modifier=Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(text = "Tidak ada data anggota")
                            Spacer(modifier = Modifier.height(20.dp))
                            Button(onClick = navigateToItemEntry){
                                Text(text = "Tambah Anggota")
                            }

                        }
                    }
                } else {
                    AnggotaLayout(
                        anggota = membersUiState.anggota,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp),
                        onDetailClick = {
                            onDetailClick(it.idAnggota)
                        }
                    )
                }

            is MembersUiState.Error -> OnError(
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
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.offline), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }

    }

}

@Composable
fun AnggotaLayout(
    anggota: List<Anggota>,
    modifier: Modifier = Modifier,
    onDetailClick: (Anggota) -> Unit,
) {
    var inputPencarian by remember { mutableStateOf("") }

    val filteredAnggota = anggota.filter {
        it.idAnggota.contains(inputPencarian, ignoreCase = true)
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
            SearchAnggotaBar(
                anggota = inputPencarian,
                onQueryChange = { inputPencarian = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( top = 10.dp)
            )
        }
        item(filteredAnggota) {
            MemberTable(anggota = filteredAnggota,
                onDetailClick = onDetailClick)
        }

    }
}

@Composable
fun SearchAnggotaBar(
    anggota: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.padding(6.dp))
    OutlinedTextField(
        value = anggota,
        onValueChange = onQueryChange,
        placeholder = {
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Cari Anggota...",
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

    Text(text = "Daftar Anggota",
        style = TextStyle(
            color = Color.DarkGray,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp)
    )
}

@Composable
fun MemberTable(
    anggota: List<Anggota>,
    onDetailClick: (Anggota) -> Unit,
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
                .background(Color.LightGray, shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                .padding(vertical = 6.dp)
        ) {
            Text(
                text = "ID Anggota",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )

            Text(
                text = "Nama",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "Email",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(2f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )
            Text(
                text = "No.Telp",
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 4.dp),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 16.sp)
            )

        }
        anggota.forEach { ag ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = ag.idAnggota,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 14.sp)

                )
                Text(
                    text = ag.nama,
                    modifier = Modifier
                        .weight(2f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = true)
                        ) {
                            onDetailClick(ag)
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
                    text = ag.email,
                    modifier = Modifier
                        .weight(2f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp)
                )
                Text(
                    text = ag.nomorTelepon,
                    modifier = Modifier
                        .weight(1.5f)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 15.sp)
                )
            }
        }
    }
}