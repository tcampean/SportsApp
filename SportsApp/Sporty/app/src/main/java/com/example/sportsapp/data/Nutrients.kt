package com.example.sportsapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Nutrients(
    val name: String,
    val amount: Float,
    val unit: String,
) : Parcelable
