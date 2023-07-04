package com.example.sportsapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Exercises", indices = [androidx.room.Index(value = ["name"], unique = true)])
data class ExerciseEntity(
    val name: String,
    val type: String,
    val muscle: String,
    val equipment: String,
    val difficulty: String,
    val instructions: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)
