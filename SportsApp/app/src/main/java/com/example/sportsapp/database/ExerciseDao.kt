package com.example.sportsapp.database

import androidx.room.*
import com.example.sportsapp.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Query("SELECT * FROM Exercises")
    fun getSavedExercises(): Flow<List<ExerciseEntity>>

    @Query("SELECT * FROM Exercises WHERE name = :exerciseName")
    fun findExercise(exerciseName: String): List<ExerciseEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(exerciseEntity: ExerciseEntity): Long

    @Query("DELETE FROM Exercises WHERE name = :exerciseName")
    fun delete(exerciseName: String): Int
}
