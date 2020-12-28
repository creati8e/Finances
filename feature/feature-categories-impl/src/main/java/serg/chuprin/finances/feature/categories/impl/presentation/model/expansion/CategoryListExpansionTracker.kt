package serg.chuprin.finances.feature.categories.impl.presentation.model.expansion

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import serg.chuprin.finances.core.api.domain.model.Id
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class CategoryListExpansionTracker @Inject constructor() {

    val expansionsFlow: Flow<Map<Id, Boolean>>
        get() = (expansionsStateFlow as Flow<Map<Id, Boolean>>).distinctUntilChanged()

    private val expansionsStateFlow = MutableStateFlow(mapOf<Id, Boolean>())

    fun isExpanded(categoryId: Id): Boolean {
        return expansionsStateFlow.value.containsKey(categoryId)
    }

    fun expand(categoryId: Id) {
        expansionsStateFlow.value = expansionsStateFlow.value + (categoryId to true)
    }

    fun collapse(categoryId: Id) {
        expansionsStateFlow.value = expansionsStateFlow.value - categoryId
    }
}