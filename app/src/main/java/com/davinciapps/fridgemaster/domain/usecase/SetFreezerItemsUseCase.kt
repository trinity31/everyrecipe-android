package com.davinciapps.fridgemaster.domain.usecase

import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.domain.repository.FreezerRepository

class SetFreezerItemsUseCase(private val freezerRepository: FreezerRepository) {
    suspend fun execute(items: List<FreezerItem>) = freezerRepository.setFreezerItems(items)
}