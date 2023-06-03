package com.example.practica5_room.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.practica5_room.data.room.models.City
import com.example.practica5_room.data.room.models.CityDao
import com.example.practica5_room.data.room.models.CountryDao
import com.example.practica5_room.data.room.models.CountryEntity
import com.example.practica5_room.data.room.models.CountryLanguage
import com.example.practica5_room.data.room.models.CountryLanguageCrossRef
import com.example.practica5_room.data.room.models.CountryLanguageDao
import com.example.practica5_room.data.room.models.GpsDao
import com.example.practica5_room.data.room.models.PositionGps
import com.example.practica5_room.data.room.models.TouristicPoint
import com.example.practica5_room.data.room.models.TouristicPointDao

@Database(
    entities = [
        CountryEntity::class,
        City::class,
        CountryLanguage::class,
        TouristicPoint::class,
        PositionGps::class,
        CountryLanguageCrossRef::class
    ], version = 1,
    exportSchema = false
)
abstract class CountryDB : RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun cityDao(): CityDao
    abstract fun languageDao(): CountryLanguageDao
    abstract fun touristicPointDao(): TouristicPointDao
    abstract fun gpsDao(): GpsDao

    companion object {
        @Volatile
        var INSTANCE: CountryDB? = null
        fun getDatabase(context: Context): CountryDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    CountryDB::class.java,
                    "country_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}