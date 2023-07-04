package com.example.sportsapp.repository

import com.example.sportsapp.database.DiaryDao
import com.example.sportsapp.entity.DiaryEntryEntity
import kotlinx.coroutines.flow.Flow

class DiaryRepository(private val dao: DiaryDao) {

    val foodEntries: Flow<List<DiaryEntryEntity>> = dao.getFoods()
    val activityEntries: Flow<List<DiaryEntryEntity>> = dao.getActivities()

    suspend fun deleteEntries(diaryEntryEntity: DiaryEntryEntity): Int {
        return dao.deleteDiaryEntry(diaryEntryEntity)
    }
}