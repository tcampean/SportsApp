package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Ingredient(
    val name: String,
    val amount: Float,
    val unit: String,
    val original: String
) : Parcelable
