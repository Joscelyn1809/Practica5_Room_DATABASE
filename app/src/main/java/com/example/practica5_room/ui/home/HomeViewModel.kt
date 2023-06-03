package com.example.practica5_room.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practica5_room.Graph
import com.example.practica5_room.data.room.models.CountryEntity
import com.example.practica5_room.data.room.models.CountryWithDetails
import com.example.practica5_room.ui.Category
import com.example.practica5_room.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository = Graph.repository
) : ViewModel() {
    var state by mutableStateOf(HomeState())
        private set

    init {
        getCountries()
    }

    private fun getCountries() {
        viewModelScope.launch {
            repository.getCountriesWithDetails.collectLatest {
                state = state.copy(
                    countries = it
                )
            }
        }
    }

    fun deleteCountry(country: CountryEntity) {
        viewModelScope.launch {
            repository.deleteCountry(country)
        }
    }

    fun onCategoryChange(category: Category) {
        state = state.copy(category = category)
        filterBy(category.id)
    }

    private fun filterBy(countryId: Int) {
        if (countryId != 1001) {
            viewModelScope.launch {
                repository.getCountriesWithDetailsFilteredById(
                    countryId
                ).collectLatest {
                    state = state.copy(countries = it)
                }
            }
        } else {
            getCountries()
        }
    }


}

data class HomeState(
    val countries: List<CountryWithDetails> = emptyList(),
    val category: Category = Category()
)