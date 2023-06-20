package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CaloricBreakdown(
    val percentProtein: Float,
    val percentFat: Float,
    val percentCarbs: Float,
) : Parcelable
