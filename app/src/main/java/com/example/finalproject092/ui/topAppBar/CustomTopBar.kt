package com.example.finalproject092.ui.topAppBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.finalproject092.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    navigateUp: () -> Unit = {},
    onRefresh: () -> Unit = {},
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.main))
            .height(100.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
            },
            navigationIcon = {
                if (canNavigateBack) {
                    IconButton(onClick = navigateUp) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "Navigate Back",
                            Modifier
                                .size(33.dp)
                                .padding(5.dp)
                        )
                    }
                }
            },
            actions = {
                IconButton(onClick = onRefresh) {
                    Icon(
                        painter = painterResource(id = R.drawable.refresh),
                        contentDescription = "Refresh",
                        Modifier
                            .size(33.dp)
                            .padding(5.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Color.Transparent, // Transparan karena background diatur di Box
                titleContentColor = colorResource(id = R.color.HomeTil),
                navigationIconContentColor = colorResource(id = R.color.HomeTil),
                actionIconContentColor = colorResource(id = R.color.HomeTil),
            ),
            scrollBehavior = scrollBehavior,
            modifier = modifier.fillMaxWidth()
        )
    }




}