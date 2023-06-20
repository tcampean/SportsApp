package com.example.sportsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sportsapp.entity.DayMealPlanEntity
import com.example.sportsapp.entity.FavoriteMealEntity
import com.example.sportsapp.entity.WeekMealPlanEntity

@Database(entities = [FavoriteMealEntity::class, DayMealPlanEntity::class, WeekMealPlanEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

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
