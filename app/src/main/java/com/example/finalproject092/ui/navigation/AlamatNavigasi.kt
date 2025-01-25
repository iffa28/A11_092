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


//Anggota
object AnggotaDestination : AlamatNavigasi {
    override val route = "anggota"
}

object InsertAnggotaDestination : AlamatNavigasi {
    override val route = "insert_anggota"
}

object DetailAnggotaDestination : AlamatNavigasi {
    override val route = "detail_anggota"
    const val idMem = "idAnggota"
    val routeWithArgs = "$route/{$idMem}"
}
object UpdateAnggotaDestination : AlamatNavigasi {
    override val route = "update_anggota"
    const val idMem = "idAnggota"
    val routeWithArgs = "$route/{$idMem}"
}

//Peminjaman
object PeminjamanDestination : AlamatNavigasi {
    override val route = "peminjaman"
}

object InsertPeminjamanDestination : AlamatNavigasi {
    override val route = "insert_peminjaman"
}

object DetailPeminjamanDestination : AlamatNavigasi {
    override val route = "detail_peminjaman"
    const val idPj = "idPeminjaman"
    val routeWithArgs = "$route/{$idPj}"
}
object UpdatePeminjamanDestination : AlamatNavigasi {
    override val route = "update_peminjaman"
    const val idPj = "idPeminjaman"
    val routeWithArgs = "$route/{$idPj}"
}


//Pengembalian
object PengembalianDestination : AlamatNavigasi {
    override val route = "returned"
}

object InsertPengembalianDestination : AlamatNavigasi {
    override val route = "insert_returned"
}

object DetailPengembalianDestination : AlamatNavigasi {
    override val route = "detail_returned"
    const val idPg = "idPengembalian"
    val routeWithArgs = "$route/{$idPg}"
}
object UpdatePengembalianDestination : AlamatNavigasi {
    override val route = "update_returned"
    const val idPg = "idPengembalian"
    val routeWithArgs = "$route/{$idPg}"
}


