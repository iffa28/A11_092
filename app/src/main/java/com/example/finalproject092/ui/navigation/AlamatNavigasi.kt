package com.example.finalproject092.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object HomeDestination : AlamatNavigasi {
    override val route = "home"
}
