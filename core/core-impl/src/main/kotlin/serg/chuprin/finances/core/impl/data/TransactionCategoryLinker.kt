package serg.chuprin.finances.core.impl.data

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.05.2020.
 */
internal class TransactionCategoryLinker @Inject constructor() {

    fun linkWithParents(
        categories: List<TransactionCategory>
    ): Map<Id, TransactionCategoryWithParent> {
        return categories.associateBy(
            { category -> category.id },
            { category ->
                val parentCategory = if (category.parentCategoryId?.value.isNullOrEmpty()) {
                    null
                } else {
                    categories.find {
                        category.parentCategoryId == it.parentCategoryId
                    }
                }
                TransactionCategoryWithParent(
                    category = category,
                    parentCategory = parentCategory
                )
            }
        )
    }

}