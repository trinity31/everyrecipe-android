package com.example.everyrecipe.domain.usecase

import com.example.everyrecipe.domain.repository.FoodRepository

class GetCategoriesWithFoodsUseCase(private val foodRepository: FoodRepository) {
}