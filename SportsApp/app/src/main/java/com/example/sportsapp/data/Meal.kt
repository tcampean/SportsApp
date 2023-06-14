package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Meal(
    val id: Int,
    val title: String,
    val readyInMinutes: Int,
    val servings: Int
) : Parcelable
