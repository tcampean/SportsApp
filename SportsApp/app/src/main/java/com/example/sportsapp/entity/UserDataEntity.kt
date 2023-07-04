package com.example.sportsapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserDataEntity(
    val goal: Int,
    val activity_level: Int,
    val gender: String,
    val weight: Int,
    val height: Int,
    val age: Int,
    @PrimaryKey val username: String,
)
