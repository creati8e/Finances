package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
typealias TransactionCategoriesMap = Map<Transaction, TransactionCategoryWithParent?>