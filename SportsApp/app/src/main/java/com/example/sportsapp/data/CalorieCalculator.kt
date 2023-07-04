package com.example.sportsapp.data

import com.example.sportsapp.entity.UserDataEntity

object CalorieCalculator {

    val activityModifiers = listOf<Double>(1.2, 1.375, 1.55, 1.725)
    val goalModifiers = listOf<Int>(-300, 0, 300, 300)

    fun getRequiredCalories(user: UserDataEntity): Int {
        return if (user.gender == "M") {
            (((66 + (13.75 * user.weight) + (5 * user.height) - (6.75 * user.age)) * activityModifiers[user.activity_level]) + goalModifiers[user.goal]).toInt()
        } else {
            (((655 + (9.56 * user.weight) + (1.85 * user.height) - (4.68 * user.age)) * activityModifiers[user.activity_level]) + goalModifiers[user.goal]).toInt()
        }
    }
}
