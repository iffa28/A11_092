package com.example.finalproject092.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.finalproject092.ui.view.HomeView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(navController = navController, startDestination = HomeDestination.route, modifier = Modifier) {
        composable(HomeDestination.route) {
            HomeView(
                onAnggotaClick ={
                    navController.navigate(AnggotaDestination.route)
                } ,
                onManageBookClick = {
                    navController.navigate(BookDestination.route)
                },
                onPinjamClick = {
                    navController.navigate(PeminjamanDestination.route)

                },
                onKembaliClick = {
                    navController.navigate(PengembalianDestination.route)
                },
                modifier = modifier
            )
        }

    }
}