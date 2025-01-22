package com.example.finalproject092

import android.app.Application
import com.example.finalproject092.depedenciesInjection.AppContainer
import com.example.finalproject092.depedenciesInjection.LibraryAppContainer

class LibraryApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = LibraryAppContainer()
    }
}