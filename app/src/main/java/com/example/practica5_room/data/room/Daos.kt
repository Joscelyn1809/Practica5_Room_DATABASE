package com.example.practica5_room.data.room.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDao {
    @Upsert
    suspend fun insert(country: CountryEntity)

    @Delete
    suspend fun delete(country: CountryEntity)

    @Query("SELECT * FROM COUNTRY_TABLE")
    fun getAllCountries(): Flow<List<CountryEntity>>

    @Query("SELECT * FROM COUNTRY_TABLE WHERE countryId =:id")
    fun getCountry(id: Int): Flow<CountryEntity>

    @Transaction
    @Query(
        """
        SELECT * FROM country_table AS CO 
        INNER JOIN city_table AS CI ON CO.countryId = CI.countryIdFk 
        INNER JOIN touristic_point_table AS TP ON CI.cityId = TP.cityIdFk 
        INNER JOIN position_gps_table AS PG ON TP.pointId = PG.pointIdFk
        INNER JOIN countries_languages AS CL ON CO.countryId = CL.countryId
        INNER JOIN country_language_table AS LT ON CL.languageId = LT.languageId
    """
    )
    fun getCountriesWithDetails(): Flow<List<CountryWithDetails>>

    @Transaction
    @Query(
        """
        SELECT * FROM country_table AS CO 
        INNER JOIN city_table AS CI ON CO.countryId = CI.countryIdFk 
        INNER JOIN touristic_point_table AS TP ON CI.cityId = TP.cityIdFk 
        INNER JOIN position_gps_table AS PG ON TP.pointId = PG.pointIdFk
        INNER JOIN countries_languages AS CL ON CO.countryId = CL.countryId
        INNER JOIN country_language_table AS LT ON CL.languageId = LT.languageId
        WHERE CO.countryId = :countryId
    """
    )
    fun getCountryWithDetailsFilteredById(countryId: Int): Flow<CountryWithDetails>

    @Transaction
    @Query(
        """
        SELECT * FROM country_table AS CO 
        INNER JOIN city_table AS CI ON CO.countryId = CI.countryIdFk 
        INNER JOIN touristic_point_table AS TP ON CI.cityId = TP.cityIdFk 
        INNER JOIN position_gps_table AS PG ON TP.pointId = PG.pointIdFk
        INNER JOIN countries_languages AS CL ON CO.countryId = CL.countryId
        INNER JOIN country_language_table AS LT ON CL.languageId = LT.languageId
        WHERE CO.countryId = :countryId
    """
    )
    fun getCountriesWithDetailsFilteredById(countryId: Int): Flow<List<CountryWithDetails>>
}

data class CountryWithDetails(
    @Embedded var country: CountryEntity,

    @Relation(parentColumn = "countryId", entityColumn = "countryIdFk")
    var cities: List<City>,

    @Relation(
        parentColumn = "countryId",
        entityColumn = "languageId",
        associateBy = Junction(CountryLanguageCrossRef::class)
    )
    var languages: List<CountryLanguage>
) {
    constructor() : this(CountryEntity(countryName = "", continent = ""), emptyList(), emptyList())
}

/*data class CountryWithDetails(
    @Embedded val country: CountryEntity,
    @Relation(
        parentColumn = "countryId",
        entityColumn = "languageId",
        associateBy = Junction(CountryLanguageCrossRef::class)
    )
    val languages: List<CountryLanguage>,
    @Relation(parentColumn = "countryId", entityColumn = "countryIdFk")
    val cities: List<City>,
    @Relation(parentColumn = "cityId", entityColumn = "cityIdFk")
    val touristicPoints: List<TouristicPoint>,
    @Relation(parentColumn = "pointId", entityColumn = "pointIdFk")
    val positionsGps: List<PositionGps>,
)*/

@Dao
interface CountryLanguageDao {
    @Upsert
    suspend fun insert(countryLanguage: CountryLanguage)

    @Delete
    suspend fun delete(countryLanguage: CountryLanguage)

    @Query("SELECT * FROM COUNTRY_LANGUAGE_TABLE")
    fun getAllLanguages(): Flow<List<CountryLanguage>>

    @Query("SELECT * FROM COUNTRY_LANGUAGE_TABLE WHERE languageId =:id")
    fun getLanguage(id: Int): Flow<CountryLanguage>
}

@Dao
interface CityDao {
    @Upsert
    suspend fun insert(city: City)

    @Delete
    suspend fun delete(city: City)

    @Query("SELECT * FROM CITY_TABLE")
    fun getAllCities(): Flow<List<City>>

    @Query("SELECT * FROM CITY_TABLE WHERE cityId =:id")
    fun getCity(id: Int): Flow<City>
}

@Dao
interface TouristicPointDao {
    @Upsert
    suspend fun insert(point: TouristicPoint)

    @Delete
    suspend fun delete(point: TouristicPoint)

    @Query("SELECT * FROM TOURISTIC_POINT_TABLE")
    fun getAllPoints(): Flow<List<TouristicPoint>>

    @Query("SELECT * FROM TOURISTIC_POINT_TABLE WHERE pointId =:id")
    fun getPoint(id: Int): Flow<TouristicPoint>
}


@Dao
interface GpsDao {
    @Upsert
    suspend fun insert(gps: PositionGps)

    @Delete
    suspend fun delete(gps: PositionGps)

    @Query("SELECT * FROM POSITION_GPS_TABLE")
    fun getAllPositions(): Flow<List<PositionGps>>

    @Query("SELECT * FROM POSITION_GPS_TABLE WHERE gpsId =:id")
    fun getPosition(id: Int): Flow<PositionGps>
}
