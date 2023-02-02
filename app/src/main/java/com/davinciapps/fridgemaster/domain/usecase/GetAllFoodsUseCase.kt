package com.davinciapps.fridgemaster.domain.usecase

import com.davinciapps.fridgemaster.domain.repository.FoodRepository

class GetAllFoodsUseCase(private val foodRepository: FoodRepository) {
}