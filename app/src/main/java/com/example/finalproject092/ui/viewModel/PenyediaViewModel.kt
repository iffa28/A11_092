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
    }
}

fun CreationExtras.appBook(): LibraryApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as LibraryApplications)