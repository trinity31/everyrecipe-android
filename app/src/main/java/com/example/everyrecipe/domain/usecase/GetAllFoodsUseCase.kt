package com.example.everyrecipe.domain.usecase

import com.example.everyrecipe.domain.repository.FoodRepository

class GetAllFoodsUseCase(private val foodRepository: FoodRepository) {
}