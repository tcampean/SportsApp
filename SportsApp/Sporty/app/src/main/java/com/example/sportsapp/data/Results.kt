package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Results(
    val results: List<Recipe>,
    val offset: Int,
    val number: Int,
    val totalResults: Int,
) : Parcelable
