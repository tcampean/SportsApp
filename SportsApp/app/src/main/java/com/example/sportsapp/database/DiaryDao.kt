package com.example.sportsapp.database

import androidx.room.*
import com.example.sportsapp.entity.DiaryEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Query("SELECT * FROM Diary WHERE calories <= 0")
    fun getActivities(): Flow<List<DiaryEntryEntity>>

    @Query("SELECT * FROM Diary WHERE calories > 0")
    fun getFoods(): Flow<List<DiaryEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertDiaryEntry(entry: DiaryEntryEntity): Long

    @Delete
    fun deleteDiaryEntry(entry: DiaryEntryEntity): Int
}
