package com.example.practica5_room.data.room.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "country_table")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    val countryId: Int = 0,
    val countryName: String,
    val continent: String,
    /**
    @ColumnInfo(name = "region") val region: String,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "surface_area") val surfaceArea: Int,
    @ColumnInfo(name = "indep_year") val indepYear: Int,
    @ColumnInfo(name = "population") val population: Int,
    @ColumnInfo(name = "life_expectancy") val lifeExpectancy: Int,
    @ColumnInfo(name = "gnp") val gnp: Int,
    @ColumnInfo(name = "gnp_oid") val gnpOid: Int,
    @ColumnInfo(name = "local_name") val localName: String,
    @ColumnInfo(name = "government_form") val governmentForm: String,
    @ColumnInfo(name = "head_of_state") val headOfState: String,
    @ColumnInfo(name = "capital") val capital: String,
    @ColumnInfo(name = "code2") val code2: String
     */
)

@Entity(tableName = "country_language_table")
data class CountryLanguage(
    @PrimaryKey(autoGenerate = true)
    val languageId: Int = 0,
    val language: String,
    val isOfficial: Boolean,
    val percentage: Int
)

@Entity(tableName = "city_table")
data class City(
    @PrimaryKey(autoGenerate = true)
    var cityId: Int = 0,
    val cityName: String,
    val city_District: String,
    val cityPopulation: Int,
    val countryIdFk: Int
)

@Entity(tableName = "touristic_point_table")
data class TouristicPoint(
    @PrimaryKey(autoGenerate = true)
    var pointId: Int = 0,
    val touristicName: String,
    val touristicDescription: String,
    val fee: Int,
    val cityIdFk: Int
)

@Entity(tableName = "position_gps_table")
data class PositionGps(
    @PrimaryKey(autoGenerate = true)
    var gpsId: Int = 0,
    val position: String,
    val pointIdFk: Int
)

@Entity(
    tableName = "countries_languages",
    primaryKeys = ["countryId", "languageId"]
)
data class CountryLanguageCrossRef(
    val countryId: Int, //Primary Key de Country Table
    val languageId: Int, //Primary Key de Language Table
)

data class CountryWithLanguages(
    @Embedded val country: CountryEntity,
    @Relation(
        parentColumn = "countryId",
        entityColumn = "languageId",
        associateBy = Junction(CountryLanguageCrossRef::class)
    )
    val languages: List<CountryLanguage>
)

data class LanguageWithCountries(
    @Embedded val language: CountryLanguage,
    @Relation(
        parentColumn = "languageId",
        entityColumn = "countryId",
        associateBy = Junction(CountryLanguageCrossRef::class)
    )
    val countries: List<CountryEntity>
)
