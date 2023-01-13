package com.example.everyrecipe.domain.usecase

import com.example.everyrecipe.data.model.FreezerItem
import com.example.everyrecipe.data.util.Resource
import com.example.everyrecipe.domain.repository.FreezerRepository
import kotlinx.coroutines.flow.Flow

class GetFreezerItemsUseCase(private val freezerRepository: FreezerRepository) {
    suspend fun execute(): Resource<List<FreezerItem>> {
        return freezerRepository.getFreezerItems()
    }
}