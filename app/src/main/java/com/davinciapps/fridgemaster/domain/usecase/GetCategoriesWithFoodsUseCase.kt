package com.davinciapps.fridgemaster.domain.usecase

import com.davinciapps.fridgemaster.domain.repository.FoodRepository

class GetCategoriesWithFoodsUseCase(private val foodRepository: FoodRepository) {
}