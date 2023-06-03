package com.example.practica5_room

import android.app.Application

class CountryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}