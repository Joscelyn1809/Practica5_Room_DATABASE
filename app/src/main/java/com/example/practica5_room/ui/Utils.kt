package com.example.practica5_room.ui

import androidx.annotation.DrawableRes
import com.example.practica5_room.R

object Utils {
    val category = listOf(
        Category(title = "País", resId = R.drawable.baseline_filter_alt_24, id = 0),
        Category(title = "Continente", resId = R.drawable.baseline_filter_alt_24, id = 1),
        Category(title = "", resId = R.drawable.baseline_filter_alt_off_24, id = 1001)
    )
}

data class Category(
    @DrawableRes val resId: Int = -1,
    val title: String = "",
    val id: Int = -1
)
