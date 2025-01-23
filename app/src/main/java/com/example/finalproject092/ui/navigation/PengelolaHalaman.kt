package com.example.finalproject092.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.finalproject092.ui.view.HomeView
import com.example.finalproject092.ui.view.anggotaView.DetailAnggotaScreen
import com.example.finalproject092.ui.view.anggotaView.EntryMemberScreen
import com.example.finalproject092.ui.view.anggotaView.HomeAnggotaView
import com.example.finalproject092.ui.view.anggotaView.UpdateAnggotaScreen
import com.example.finalproject092.ui.view.bookView.DetailBookScreen
import com.example.finalproject092.ui.view.bookView.EntryBookScreen
import com.example.finalproject092.ui.view.bookView.HomeBookView
import com.example.finalproject092.ui.view.bookView.UpdateBookScreen

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

        //Buku
        composable(BookDestination.route) {
            HomeBookView(
                navigateToItemEntry = {
                    navController.navigate(InsertBookDestination.route)
                },
                navigateBack = {
                    navController.popBackStack()
                },
                onDetailBukuClick = { idBuku->
                    navController.navigate("${DetailBookDestination.route}/$idBuku")

                },
                onEditBukuClick = { idBuku ->
                    navController.navigate("${UpdateBookDestination.route}/$idBuku")
                }
            )
        }

        composable(InsertBookDestination.route) {
            EntryBookScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            DetailBookDestination.routeWithArgs,
            arguments = listOf(
                navArgument(DetailBookDestination.idBook) {
                    type = NavType.IntType
                }
            )
        ) {
            val idBuku = it.arguments?.getInt(DetailBookDestination.idBook)
            idBuku?.let { idBuku ->
                DetailBookScreen(
                    navigateBack = { navController.navigateUp() },

                    )
            }

        }

        composable(
            UpdateBookDestination.routeWithArgs,
            arguments = listOf(
                navArgument(UpdateBookDestination.idBook) {
                    type = NavType.IntType
                }
            )
        ) {
            UpdateBookScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateUp = { navController.navigate(BookDestination.route)
                {
                    popUpTo(BookDestination.route) {
                        inclusive = true
                    }
                }
                }
            )
        }


        //Anggota
        composable(AnggotaDestination.route) {
            HomeAnggotaView(
                navigateToItemEntry = {
                    navController.navigate(InsertAnggotaDestination.route)
                },
                navigateBack = {
                    navController.popBackStack()
                } ,
                onDetailClick = { idAnggota ->
                    navController.navigate("${DetailAnggotaDestination.route}/$idAnggota")
                },
                onEditMemberClick = {idAnggota ->
                    navController.navigate("${UpdateAnggotaDestination.route}/$idAnggota")
                }
            )
        }

        composable(InsertAnggotaDestination.route) {
            EntryMemberScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            DetailAnggotaDestination.routeWithArgs,
            arguments = listOf(
                navArgument(DetailAnggotaDestination.idMem) {
                    type = NavType.StringType
                }
            )
        ) {
            val idBuku = it.arguments?.getString(DetailAnggotaDestination.idMem)
            idBuku?.let { idBuku ->
                DetailAnggotaScreen(
                    navigateBack = { navController.navigateUp() },
                )
            }

        }

        composable(
            UpdateAnggotaDestination.routeWithArgs,
            arguments = listOf(
                navArgument(UpdateAnggotaDestination.idMem) {
                    type = NavType.StringType
                }
            )
        ) {
            UpdateAnggotaScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateUp = { navController.navigate(AnggotaDestination.route)
                {
                    popUpTo(AnggotaDestination.route) {
                        inclusive = true
                    }
                }
                }
            )
        }

    }
}