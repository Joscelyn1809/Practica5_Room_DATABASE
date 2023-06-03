package com.example.practica5_room.ui.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.practica5_room.Graph
import com.example.practica5_room.data.room.models.City
import com.example.practica5_room.data.room.models.CountryEntity
import com.example.practica5_room.data.room.models.CountryLanguage
import com.example.practica5_room.ui.Category
import com.example.practica5_room.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailViewModel
constructor(
    private val countryId: Int,
    private val repository: Repository = Graph.repository
) : ViewModel() {
    var state by mutableStateOf(DetailState())
        private set

    init {
        addCountry()
        getCountries()
        if (countryId != -1) {
            viewModelScope.launch {
                repository.getCountryWithDetails(countryId).collectLatest {
                    state = state.copy(
                        countryName = it.country.countryName,
                        countryContinent = it.country.continent

                    )
                }
            }
        }
    }

    val isFieldNotEmpty: Boolean
        get() = state.countryName.isNotEmpty() &&
                state.countryContinent.isNotEmpty()

    fun onCountryNameChange(newValue: String) {
        state = state.copy(countryName = newValue)
    }

    fun onCountryContinentChange(newValue: String) {
        state = state.copy(countryContinent = newValue)
    }

    fun onScreenDialogDismissed(newValue: Boolean) {
        state = state.copy(isScreenDialogDismissed = newValue)
    }

    fun onCategoryChange(newValue: Category) {
        state = state.copy(category = newValue)
    }

    fun onCountryLanguageChange(newValue: String) {
        state = state.copy(languageName = newValue)
    }

    fun addCountry() {
        viewModelScope.launch {
            repository.insertCountry(
                CountryEntity(
                    countryName = "Mexico", //state.countryName,
                    continent = "Estados Unidos de America"//state.countryContinent
                )
            )
            repository.insertCountry(
                CountryEntity(
                    countryName = "Estados Unidos", //state.countryName,
                    continent = "Estados Unidos de America"//state.countryContinent
                )
            )
        }
    }

    fun updateCountry(id: Int) {
        viewModelScope.launch {
            repository.insertCountry(
                CountryEntity(
                    countryName = state.countryName,
                    continent = state.countryContinent,
                    countryId = id
                )
            )
        }
    }

    /*fun addCity() {
        viewModelScope.launch {
            repository.insertCity(
                City(
                    cityName = "Mexicali", //state.countryName,
                    city_District = "Hola",//state.countryContinent
                    cityPopulation = 120394,
                    countryIdFk = state.countryList.get(countryId).countryId
                    *//*state.countryList.find {
                        it.countryName == state.cityList.
                    }?.countryId ?: 0*//*
                )
            )
        }
    }*/

    fun getCountries() {
        viewModelScope.launch {
            repository.countries.collectLatest {
                state = state.copy(countryList = it)
            }
        }
    }

    fun getCities() {
        viewModelScope.launch {
            repository.cities.collectLatest {
                state = state.copy(cityList = it)
            }
        }
    }
}

class DetailViewModelFactor(private val id: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(countryId = id) as T
    }
}

data class DetailState(
    val countryList: List<CountryEntity> = emptyList(),
    val cityList: List<City> = emptyList(),
    val languageList: List<CountryLanguage> = emptyList(),
    val languageName: String = "",
    val countryName: String = "",
    val countryContinent: String = "",
    val isScreenDialogDismissed: Boolean = true,
    val isUpdatingCountry: Boolean = false,
    val category: Category = Category()
)