package com.example.sportsapp.repository

import com.example.sportsapp.database.ExerciseDao
import com.example.sportsapp.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

class ExerciseRepository(private val dao: ExerciseDao) {

    val exerciseList: Flow<List<ExerciseEntity>> = dao.getSavedExercises()

    suspend fun insert(exercise: ExerciseEntity): Long {
        return dao.insert(exercise)
    }

    suspend fun delete(exercise: ExerciseEntity): Int {
        return dao.delete(exercise.name)
    }

    suspend fun exists(exercise: ExerciseEntity): Boolean {
        return dao.findExercise(exercise.name).isEmpty()
    }
}