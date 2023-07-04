package com.example.sportsapp.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Diary")
data class DiaryEntryEntity(
    val label: String,
    val calories: Int,
    val date: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
