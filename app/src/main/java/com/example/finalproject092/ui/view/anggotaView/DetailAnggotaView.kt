package com.example.finalproject092.ui.view.anggotaView

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
import com.example.finalproject092.model.Anggota
import com.example.finalproject092.ui.topAppBar.CustomTopBar
import com.example.finalproject092.ui.viewModel.PenyediaViewModel
import com.example.finalproject092.ui.viewModel.anggotaViewModel.DetailAnggotaViewModel
import com.example.finalproject092.ui.viewModel.anggotaViewModel.DetailMemUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailAnggotaScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    detailViewModel: DetailAnggotaViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
                    detailViewModel.getMemberbyId()
                }
            )
        },
    )
    { innerPadding ->
        DetailStatus(
            memUiState = detailViewModel.detailMemUiState,
            retryAction = {detailViewModel.getMemberbyId() },
            modifier = Modifier.padding(innerPadding),

        )
    }
}

@Composable
fun DetailStatus(
    memUiState: DetailMemUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
){
    when(memUiState){
        is DetailMemUiState.Success -> {
            DetailMemberLayout(
                anggota = memUiState.anggota,
                modifier = modifier
            )
        }
        is DetailMemUiState.Loading -> OnLoading(modifier = modifier)
        is DetailMemUiState.Error -> OnError(retryAction, modifier = modifier)
    }
}

@Composable
fun DetailMemberLayout(
    anggota: Anggota,
    modifier: Modifier = Modifier,
) {
    Column(modifier = Modifier.padding(top = 90.dp)){
        ItemDetailMem(
            anggota = anggota,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(10.dp))
    }

}

@Composable
fun ItemDetailMem(
    modifier: Modifier = Modifier,
    anggota: Anggota
)
{
    Column(modifier = Modifier.background(color = Color.LightGray)
    ) {
        ComponentDetailMember(judul = "Nama", isinya = anggota.nama)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMember(judul = "ID Anggota", isinya = anggota.idAnggota)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMember(judul = "Email", isinya = anggota.email)
        Spacer(modifier = Modifier.padding(4.dp))

        ComponentDetailMember(judul = "Nomor Telepon", isinya = anggota.nomorTelepon)
        Spacer(modifier = Modifier.padding(4.dp))
    }
}

@Composable
fun ComponentDetailMember(
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
