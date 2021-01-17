package serg.chuprin.finances.feature.categories.list.presentation.model.store

import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
sealed class CategoriesListEvent {

    class CloseScreenWithPickerCategory(
        val categoryId: Id
    ) : CategoriesListEvent()

}