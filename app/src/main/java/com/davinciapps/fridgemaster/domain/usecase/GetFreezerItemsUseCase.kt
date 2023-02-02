package com.davinciapps.fridgemaster.domain.usecase

import com.davinciapps.fridgemaster.data.model.FreezerItem
import com.davinciapps.fridgemaster.data.util.Resource
import com.davinciapps.fridgemaster.domain.repository.FreezerRepository

class GetFreezerItemsUseCase(private val freezerRepository: FreezerRepository) {
    suspend fun execute(): Resource<List<FreezerItem>> {
        return freezerRepository.getFreezerItems()
    }
}