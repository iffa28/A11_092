package com.example.finalproject092.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject092.R
import com.example.finalproject092.ui.topAppBar.HomeTopBar

@Composable
fun HomeView(
    onAnggotaClick: () -> Unit = {},
    onManageBookClick: () -> Unit = {},
    onPinjamClick: () -> Unit = {},
    onKembaliClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            HomeTopBar()
        }

    ) { innerPadding ->
        BodyHomeView(
            onMembersClick = {onAnggotaClick()},
            onBorrowClick = {onPinjamClick()},
            onReturnClick = {onKembaliClick()},
            onBookClick = {onManageBookClick()},
            modifier = modifier.padding(innerPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BodyHomeView(
    onBookClick: () -> Unit = {},
    onMembersClick: () -> Unit = {},
    onBorrowClick: () -> Unit = {},
    onReturnClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(modifier = Modifier
        .padding(20.dp)
        .fillMaxWidth()
        ,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Box(){
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                ElevatedCard(
                    onClick = onBookClick,
                    modifier = modifier
                        .padding(5.dp)
                        .size(165.dp)
                        .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(13.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img),
                            contentDescription = "", Modifier
                                .size(80.dp)
                                .padding(5.dp)
                        )
                        Text(text = "BUKU",
                            style = TextStyle(
                                color = colorResource(id = R.color.HomeTil),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }

                ElevatedCard(
                    onClick = onMembersClick,
                    modifier = modifier
                        .padding(5.dp)
                        .size(165.dp)
                        .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(13.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.team),
                            contentDescription = "", Modifier
                                .size(80.dp)
                                .padding(5.dp)
                        )
                        Text(text = "ANGGOTA",
                            style = TextStyle(
                                color = colorResource(id = R.color.HomeTil),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier
                .padding(top = 200.dp),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(20 .dp)) {
                ElevatedCard(
                    onClick = onBorrowClick,
                    modifier = modifier
                        .padding(5.dp)
                        .size(165.dp)
                        .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(13.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.borrow),
                            contentDescription = "", Modifier
                                .size(80.dp)
                                .padding(5.dp)
                        )
                        Text(text = "PEMINJAMAN",
                            style = TextStyle(
                                color = colorResource(id = R.color.HomeTil),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }

                ElevatedCard(
                    onClick = onReturnClick,
                    modifier = modifier
                        .padding(5.dp)
                        .size(165.dp)
                        .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(13.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.book),
                            contentDescription = "", Modifier
                                .size(80.dp)
                                .padding(5.dp)
                        )
                        Text(text = "PENGEMBALIAN",
                            style = TextStyle(
                                color = colorResource(id = R.color.HomeTil),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier.padding(top = 5.dp)
                        )
                    }
                }
            }

        }

    }

}

