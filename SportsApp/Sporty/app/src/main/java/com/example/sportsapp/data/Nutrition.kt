package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nutrition(
    val nutrients: List<Nutrients>,
    val caloricBreakdown: CaloricBreakdown,
    val weightPerServing: WeightPerServing
) : Parcelable

