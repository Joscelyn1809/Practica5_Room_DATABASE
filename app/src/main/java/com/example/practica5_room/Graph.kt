package com.example.practica5_room

import android.content.Context
import com.example.practica5_room.data.room.CountryDB
import com.example.practica5_room.ui.repository.Repository

object Graph {
    lateinit var db: CountryDB
        private set

    val repository by lazy {
        Repository(
            countryDao = db.countryDao(),
            cityDao = db.cityDao(),
            languageDao = db.languageDao(),
            touristicPointDao = db.touristicPointDao(),
            gpsDao = db.gpsDao()
        )
    }

    fun provide(context:Context){
        db = CountryDB.getDatabase(context)
    }
}