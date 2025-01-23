package com.example.finalproject092.ui.navigation

interface AlamatNavigasi {
    val route: String
}

object HomeDestination : AlamatNavigasi {
    override val route = "home"
}

//Buku
object BookDestination : AlamatNavigasi {
    override val route = "book"
}

object InsertBookDestination : AlamatNavigasi {
    override val route = "insert_book"
}

object DetailBookDestination : AlamatNavigasi {
    override val route = "detail_book"
    const val idBook = "idBuku"
    val routeWithArgs = "$route/{$idBook}"
}
object UpdateBookDestination : AlamatNavigasi {
    override val route = "update_book"
    const val idBook = "idBuku"
    val routeWithArgs = "$route/{$idBook}"
}

