package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.category.TransactionCategory
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 24.12.2020.
 */
class TransactionCategoryShares(
    map: Map<TransactionCategory?, BigDecimal>
) : Map<TransactionCategory?, BigDecimal> by map