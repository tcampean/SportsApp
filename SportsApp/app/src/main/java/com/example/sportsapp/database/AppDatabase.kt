package com.example.sportsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sportsapp.entity.*

@Database(entities = [FavoriteMealEntity::class, DayMealPlanEntity::class, WeekMealPlanEntity::class, ExerciseEntity::class, UserDataEntity::class, DiaryEntryEntity::class], version = 10)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao
    abstract fun mealPlanDao(): MealPlanDao

    abstract fun exerciseDao(): ExerciseDao

    abstract fun userDao(): UserDao

    abstract fun diaryDao(): DiaryDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            var value = instance
            if (value == null) {
                value = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sporty_database",
                )
                    .fallbackToDestructiveMigration()
                    .build()
                instance = value
            }

            return value
        }
    }
}
