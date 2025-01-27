package com.example.finalproject092.ui.viewModel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.finalproject092.LibraryApplications
import com.example.finalproject092.ui.viewModel.anggotaViewModel.DetailAnggotaViewModel
import com.example.finalproject092.ui.viewModel.anggotaViewModel.HomeAnggotaViewModel
import com.example.finalproject092.ui.viewModel.anggotaViewModel.InsertAnggotaViewModel
import com.example.finalproject092.ui.viewModel.anggotaViewModel.UpdateAnggotaViewModel
import com.example.finalproject092.ui.viewModel.bookViewModel.DetailBookViewModel
import com.example.finalproject092.ui.viewModel.bookViewModel.HomeBookViewModel
import com.example.finalproject092.ui.viewModel.bookViewModel.InsertBookViewModel
import com.example.finalproject092.ui.viewModel.bookViewModel.UpdateBookViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.DetailPeminjamanViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.HomePeminjamanViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.InsertPeminjamanViewModel
import com.example.finalproject092.ui.viewModel.peminjamanViewModel.UpdatePeminjamanViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.DetailPengembalianViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.HomePengembalianViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.InsertPengembalianViewModel
import com.example.finalproject092.ui.viewModel.pengembalianViewModel.UpdatePengembalianViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeBookViewModel(
                appBook().container.bukuRepository
            )
        }
        initializer {
            InsertBookViewModel(
                appBook().container.bukuRepository
            )
        }

        initializer {
            DetailBookViewModel(
                createSavedStateHandle(),
                appBook().container.bukuRepository
            )
        }

        initializer {
            UpdateBookViewModel(
                createSavedStateHandle(),
                appBook().container.bukuRepository
            )
        }


        initializer {
            HomeAnggotaViewModel(
                appBook().container.anggotaRepository
            )
        }

        initializer {
            InsertAnggotaViewModel(
                appBook().container.anggotaRepository
            )
        }

        initializer {
            DetailAnggotaViewModel(
                createSavedStateHandle(),
                appBook().container.anggotaRepository
            )
        }

        initializer {
            UpdateAnggotaViewModel(
                createSavedStateHandle(),
                appBook().container.anggotaRepository
            )
        }

        //peminjaman
        initializer {
            HomePeminjamanViewModel(
                appBook().container.peminjamanRepository
            )
        }

        initializer {
            InsertPeminjamanViewModel(
                appBook().container.peminjamanRepository,
                appBook().container.bukuRepository,
                appBook().container.anggotaRepository

            )
        }

        initializer {
            DetailPeminjamanViewModel(
                createSavedStateHandle(),
                appBook().container.peminjamanRepository
            )
        }

        initializer {
            UpdatePeminjamanViewModel(
                createSavedStateHandle(),
                appBook().container.peminjamanRepository,
                appBook().container.bukuRepository,
                appBook().container.anggotaRepository
            )
        }

        //pengembalian
        initializer {
            HomePengembalianViewModel(
                appBook().container.pengembalianRepository
            )
        }
        initializer {
            InsertPengembalianViewModel(
                appBook().container.pengembalianRepository,
                appBook().container.peminjamanRepository
            )
        }

        initializer {
            DetailPengembalianViewModel(
                createSavedStateHandle(),
                appBook().container.pengembalianRepository
            )
        }

        initializer {
            UpdatePengembalianViewModel(
                createSavedStateHandle(),
                appBook().container.pengembalianRepository,
                appBook().container.peminjamanRepository
            )
        }
    }
}

fun CreationExtras.appBook(): LibraryApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LibraryApplications)