package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent

/**
 * Created by Sergey Chuprin on 12.12.2020.
 */
class CategoriesQueryResult(
    map: Map<Id, TransactionCategoryWithParent>
) : Map<Id, TransactionCategoryWithParent> by map