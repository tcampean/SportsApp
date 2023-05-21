package com.example.sportsapp.viewmodels

import androidx.lifecycle.ViewModel
import com.example.sportsapp.R
import com.example.sportsapp.data.MealPlanCategory

class MealPlanViewModel : ViewModel() {
    val mealPlanCategories = listOf<MealPlanCategory>(
        MealPlanCategory("Gluten Free", R.drawable.gluten_free_card_image),
        MealPlanCategory("Ketogenic", R.drawable.keto_card_image),
        MealPlanCategory("Vegetarian", R.drawable.vegetarian_card_image),
        MealPlanCategory("Lacto-Vegetarian", R.drawable.lacto_vegetarian_card_image),
        MealPlanCategory("Ovo-Vegetarian", R.drawable.ovo_vegetarian_card_image),
        MealPlanCategory("Vegan", R.drawable.vegan_card_image),
        MealPlanCategory("Pescetarian", R.drawable.pescetarian_card_image),
        MealPlanCategory("Paleo", R.drawable.paleo_card_image),
    )

    private var mealPlanLength = ""
    private var selectedDiet = ""

    fun setMealPlanLength(length: String) {
        mealPlanLength = length
        println("Meal plan length is " + mealPlanLength)
    }

    fun setSelectedDiet(diet: String) {
        selectedDiet = diet
        println("Diet is " + selectedDiet)
    }
}
