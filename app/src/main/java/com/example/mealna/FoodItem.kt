package com.example.mealna

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FoodItem(
    val imageUrl: String, // URL or local resource ID
    val title: String,
    val description: String,
    val rating: Float,
    val price: String,
    val calories: String,
    val carbs: String,
    val protein: String,
    val fat: String,
    var isFavorite: Boolean = false
) : Parcelable
