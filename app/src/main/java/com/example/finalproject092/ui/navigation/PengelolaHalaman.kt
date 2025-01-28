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
import com.example.finalproject092.ui.view.peminjamanView.DetailPeminjamanScreen
import com.example.finalproject092.ui.view.peminjamanView.EntryBorrowScreen
import com.example.finalproject092.ui.view.peminjamanView.HomeBorrowView
import com.example.finalproject092.ui.view.peminjamanView.UpdatePeminjamanScreen
import com.example.finalproject092.ui.view.pengembalianView.DetailPengembalianScreen
import com.example.finalproject092.ui.view.pengembalianView.EntryReturnScreen
import com.example.finalproject092.ui.view.pengembalianView.HomeReturnedBookView
import com.example.finalproject092.ui.view.pengembalianView.UpdatePengembalianScreen

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
                    type = NavType.StringType
                }
            )
        ) {
            val idBuku = it.arguments?.getString(DetailBookDestination.idBook)
            idBuku?.let { idBuku ->
                DetailBookScreen(
                    navigateBack = { navController.navigateUp() },
                    onEditBukuClick = {navController.navigate("${UpdateBookDestination.route}/$idBuku")}
                )
            }

        }

        composable(
            UpdateBookDestination.routeWithArgs,
            arguments = listOf(
                navArgument(UpdateBookDestination.idBook) {
                    type = NavType.StringType
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
                    onEditMemberClick = {navController.navigate("${UpdateAnggotaDestination.route}/$idBuku")}
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

        composable(PeminjamanDestination.route) {
            HomeBorrowView(
                navigateToItemEntry = {
                    navController.navigate(InsertPeminjamanDestination.route)
                },
                navigateBack = {
                    navController.popBackStack()
                } ,
                onDetailPjClick = { idPeminjaman ->
                    navController.navigate("${DetailPeminjamanDestination.route}/$idPeminjaman")
                }
            )
        }

        composable(InsertPeminjamanDestination.route) {
            EntryBorrowScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            DetailPeminjamanDestination.routeWithArgs,
            arguments = listOf(
                navArgument(DetailPeminjamanDestination.idPj) {
                    type = NavType.IntType
                }
            )
        ) {
            val idPeminjaman = it.arguments?.getInt(DetailPeminjamanDestination.idPj)
            idPeminjaman?.let { idPeminjaman ->
                DetailPeminjamanScreen(
                    navigateBack = { navController.navigateUp() },
                    onEditPjClick = {navController.navigate("${UpdatePeminjamanDestination.route}/$idPeminjaman")}
                )
            }

        }

        composable(
            UpdatePeminjamanDestination.routeWithArgs,
            arguments = listOf(
                navArgument(UpdatePeminjamanDestination.idPj) {
                    type = NavType.IntType
                }
            )
        ) {
            UpdatePeminjamanScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateUp = { navController.navigate(PeminjamanDestination.route)
                {
                    popUpTo(PeminjamanDestination.route) {
                        inclusive = true
                    }
                }
                }
            )
        }


        //pengembalian
        composable(PengembalianDestination.route) {
            HomeReturnedBookView(
                navigateToItemEntry = {
                    navController.navigate(InsertPengembalianDestination.route)
                },
                navigateBack = {
                    navController.popBackStack()
                } ,
                onDetailPgClick = { idReturn ->
                    navController.navigate("${DetailPengembalianDestination.route}/$idReturn")
                },
            )
        }

        composable(InsertPengembalianDestination.route) {
            EntryReturnScreen(
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            DetailPengembalianDestination.routeWithArgs,
            arguments = listOf(
                navArgument(DetailPengembalianDestination.idPg) {
                    type = NavType.IntType
                }
            )
        ) {
            val idReturn = it.arguments?.getInt(DetailPengembalianDestination.idPg)
            idReturn?.let { idReturn ->
                DetailPengembalianScreen(
                    navigateBack = { navController.navigateUp() },
                    onEditPgClick = {navController.navigate("${UpdatePengembalianDestination.route}/$idReturn")}
                )
            }

        }

        composable(
            UpdatePengembalianDestination.routeWithArgs,
            arguments = listOf(
                navArgument(UpdatePengembalianDestination.idPg) {
                    type = NavType.IntType
                }
            )
        ) {
            UpdatePengembalianScreen(
                navigateBack = { navController.navigateUp() },
                onNavigateUp = { navController.navigate(PengembalianDestination.route)
                {
                    popUpTo(PengembalianDestination.route) {
                        inclusive = true
                    }
                }
                }
            )
        }

    }
}