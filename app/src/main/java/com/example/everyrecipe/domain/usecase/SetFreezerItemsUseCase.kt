package com.example.everyrecipe.domain.usecase

import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.domain.repository.FreezerRepository

class SetFreezerItemsUseCase(private val freezerRepository: FreezerRepository) {
    suspend fun execute(items: List<FreezerItem>) = freezerRepository.setFreezerItems(items)
}