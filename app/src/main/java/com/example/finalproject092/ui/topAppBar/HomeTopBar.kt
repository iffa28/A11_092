package com.example.finalproject092.ui.topAppBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject092.R

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier
    ) {
        Column(modifier= Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.HomeT),
                shape = RoundedCornerShape(bottomEnd = 110.dp)
            )
        )
        {
            Row(Modifier.padding(10.dp)
                .padding(end = 10.dp)
                .padding(bottom = 45.dp)) {

                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    horizontalAlignment = Alignment.Start)
                {
                    Text(
                        text = "BacaPedia",
                        color = colorResource(id = R.color.HomeTil),
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            fontSize = 45.sp
                        )
                    )

                    Text(
                        text = "perpustakaan Umy",
                        color = colorResource(id = R.color.HomeTil),
                        style = TextStyle(
                            fontSize = 20.sp
                        )
                    )

                }
                Column(Modifier.padding(top = 25.dp)
                    .fillMaxWidth(),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.logoumy),
                        contentDescription = "", Modifier.size(48.dp)
                            .clip(CircleShape)

                    )
                }

            }

        }


    }


}