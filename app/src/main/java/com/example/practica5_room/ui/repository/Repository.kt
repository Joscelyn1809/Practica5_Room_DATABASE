package com.example.practica5_room.ui.repository

import com.example.practica5_room.data.room.models.City
import com.example.practica5_room.data.room.models.CityDao
import com.example.practica5_room.data.room.models.CountryDao
import com.example.practica5_room.data.room.models.CountryEntity
import com.example.practica5_room.data.room.models.CountryLanguage
import com.example.practica5_room.data.room.models.CountryLanguageDao
import com.example.practica5_room.data.room.models.GpsDao
import com.example.practica5_room.data.room.models.PositionGps
import com.example.practica5_room.data.room.models.TouristicPoint
import com.example.practica5_room.data.room.models.TouristicPointDao

class Repository(
    private val countryDao: CountryDao,
    private val cityDao: CityDao,
    private val languageDao: CountryLanguageDao,
    private val touristicPointDao: TouristicPointDao,
    private val gpsDao: GpsDao
) {
    var countries = countryDao.getAllCountries()
    val getCountriesWithDetails = countryDao.getCountriesWithDetails()

    fun getCountryWithDetails(id: Int) =
        countryDao.getCountryWithDetailsFilteredById(id)

    fun getCountryWithDetailsFilteredById(id: Int) =
        countryDao.getCountryWithDetailsFilteredById(id)

    fun getCountriesWithDetailsFilteredById(id: Int) =
        countryDao.getCountriesWithDetailsFilteredById(id)


    var cities = cityDao.getAllCities()
    var languages = languageDao.getAllLanguages()
    var points = touristicPointDao.getAllPoints()
    var positions = gpsDao.getAllPositions()

    fun getCountry(id: Int) {
        countryDao.getCountry(id)
    }


    suspend fun insertCountry(country: CountryEntity) {
        countryDao.insert(country)
    }

    suspend fun deleteCountry(country: CountryEntity) {
        countryDao.delete(country)
    }

    suspend fun insertCity(city: City) {
        cityDao.insert(city)
    }

    suspend fun deleteCity(city: City) {
        cityDao.delete(city)
    }

    suspend fun insertLanguage(language: CountryLanguage) {
        languageDao.insert(language)
    }

    suspend fun deleteLanguage(language: CountryLanguage) {
        languageDao.delete(language)
    }

    suspend fun insertTouristicPoint(point: TouristicPoint) {
        touristicPointDao.insert(point)
    }

    suspend fun deleteTouristicPoint(point: TouristicPoint) {
        touristicPointDao.delete(point)
    }

    suspend fun insertGPS(gps: PositionGps) {
        gpsDao.insert(gps)
    }

    suspend fun deleteGPS(gps: PositionGps) {
        gpsDao.delete(gps)
    }
}