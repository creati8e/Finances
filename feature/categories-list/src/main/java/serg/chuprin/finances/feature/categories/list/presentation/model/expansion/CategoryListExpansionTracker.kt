package serg.chuprin.finances.feature.categories.list.presentation.model.expansion

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class CategoryListExpansionTracker {

    val expansionsFlow: Flow<Map<Id, Boolean>>
        get() = expansionsStateFlow

    private val expansionsStateFlow = MutableStateFlow(mapOf<Id, Boolean>())

    fun toggleExpansion(categoryId: Id) {
        if (expansionsStateFlow.value[categoryId] == true) {
            expansionsStateFlow.value = expansionsStateFlow.value - categoryId
        } else {
            expansionsStateFlow.value = expansionsStateFlow.value + (categoryId to true)
        }
    }

}