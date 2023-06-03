package com.example.practica5_room.ui.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.practica5_room.ui.Category

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: Int,
    navigateUp: () -> Unit
) {
    val viewModel = viewModel<DetailViewModel>(factory = DetailViewModelFactor(id))

    Scaffold {
        DetailEntry(
            state = viewModel.state,
            onCountryChange = viewModel::onCountryNameChange,
            onCountryContinentChange = viewModel::onCountryContinentChange,
            onCountryLanguageChange = viewModel::onCountryLanguageChange,
            onCategoryChange = viewModel::onCategoryChange,
            onDialogDismissed = viewModel::onScreenDialogDismissed,
            onSaveCountry = { viewModel::addCountry },
            updateCountry = { viewModel.updateCountry(id) },
            saveCountry = viewModel::addCountry,
            onUpdateCountry = { viewModel.updateCountry(id) },
        ) {
            navigateUp.invoke()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailEntry(
    modifier: Modifier = Modifier,
    state: DetailState,
    onCountryChange: (String) -> Unit,
    onCountryContinentChange: (String) -> Unit,
    onCountryLanguageChange: (String) -> Unit,
    onCategoryChange: (Category) -> Unit,
    onDialogDismissed: (Boolean) -> Unit,
    onSaveCountry: () -> Unit,
    onUpdateCountry: () -> Unit,
    updateCountry: () -> Unit,
    saveCountry: () -> Unit,
    navigateUp: () -> Unit
) {

    var isNewEnabled by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
    ) {
        TextField(
            value = state.countryName,
            onValueChange = { onCountryChange(it) },
            label = { Text(text = "Nombre del País") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            //shape = RoundedCornerShape(CircleShape)
        )

        Spacer(modifier = Modifier.Companion.size(12.dp))

        TextField(
            value = state.countryContinent,
            onValueChange = { onCountryContinentChange(it) },
            label = { Text(text = "Continente") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            //shape = RoundedCornerShape(CircleShape)
        )

        Row {
            TextField(
                value = state.countryContinent, onValueChange = {
                    if (isNewEnabled) onCountryChange.invoke(it)
                },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.textFieldColors(
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                label = { Text(text = "Ciudad") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                        }
                    )
                }
            )
            if (!state.isScreenDialogDismissed) {
                Popup(onDismissRequest = {
                    onDialogDismissed.invoke(!state.isScreenDialogDismissed)
                }) {
                    Surface(modifier = Modifier.padding(16.dp)) {
                        Column() {
                            state.languageList.forEach {
                                Text(
                                    text = it.language,
                                    modifier = Modifier
                                        .padding(8.dp)
                                        .clickable {
                                            onCountryLanguageChange.invoke(it.language)
                                            onDialogDismissed(!state.isScreenDialogDismissed)
                                        },
                                )
                            }
                        }
                    }
                }
            }
            TextButton(onClick = {
                isNewEnabled = if (isNewEnabled) {
                    onSaveCountry.invoke()
                    !isNewEnabled
                } else {
                    !isNewEnabled
                }
            }) {
                Text(text = if (isNewEnabled) "Guardar" else "Nuevo")
            }
        }

        val buttonTitle = if (state.isUpdatingCountry) "Actualizar" else "Añadir"
        Button(
            onClick = {
                when (state.isUpdatingCountry) {
                    true -> {
                        onUpdateCountry.invoke()
                    }

                    false -> {
                        saveCountry.invoke()
                    }
                }
                navigateUp.invoke()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = state.countryName.isNotEmpty() &&
                    state.countryContinent.isNotEmpty()
        ) {
            Text(text = buttonTitle)
        }
    }
}