package com.example.sportsapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.sportsapp.entity.UserDataEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM User")
    fun getUser(): List<UserDataEntity>

    @Query("DELETE FROM User")
    fun clearUser(): Int

    @Insert
    fun insertUser(user: UserDataEntity)

    @Query("UPDATE User SET weight = :newWeight")
    fun updateWeight(newWeight: Int)

}
