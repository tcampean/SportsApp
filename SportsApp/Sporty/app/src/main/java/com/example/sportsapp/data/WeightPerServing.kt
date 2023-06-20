package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeightPerServing(
    val amount: Int,
    val unit: String,
) : Parcelable
