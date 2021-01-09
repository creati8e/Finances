package serg.chuprin.finances.feature.dashboard.domain.model

import serg.chuprin.finances.core.api.domain.model.CategoryShares
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 27.04.2020.
 */
data class DashboardCategoriesWidgetPage(
    /**
     * Represents total amount of all [categoryShares].
     * Equals [BigDecimal.ZERO] if [categoryShares] is empty.
     */
    val totalAmount: BigDecimal,
    /**
     * Represents amount of all categories not included in [categoryShares].
     * Could be [BigDecimal.ZERO].
     */
    val otherCategoriesShare: BigDecimal,
    /**
     * Represents categories associated with amount.
     */
    val categoryShares: CategoryShares,
    /**
     * Represents "other" categories associated with amount.
     */
    val otherCategoryShares: CategoryShares?,
    val transactionType: PlainTransactionType
)