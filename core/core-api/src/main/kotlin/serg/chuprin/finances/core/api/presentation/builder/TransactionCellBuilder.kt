package serg.chuprin.finances.core.api.presentation.builder

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import serg.chuprin.finances.core.api.presentation.model.cells.TransactionCell

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
interface TransactionCellBuilder {

    fun build(
        transaction: Transaction,
        categoryWithParent: TransactionCategoryWithParent?
    ): TransactionCell

}