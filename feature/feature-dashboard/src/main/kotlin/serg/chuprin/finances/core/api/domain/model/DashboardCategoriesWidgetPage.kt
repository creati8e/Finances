package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import serg.chuprin.finances.feature.dashboard.domain.builder.categories.CategoryAmounts
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 27.04.2020.
 */
data class DashboardCategoriesWidgetPage(
    /**
     * Represents total amount of all [categoryAmounts].
     * Equals [BigDecimal.ZERO] if [categoryAmounts] is empty.
     */
    val totalAmount: BigDecimal,
    /**
     * Represents amount of all categories not included in [categoryAmounts].
     * Could be [BigDecimal.ZERO].
     */
    val otherAmount: BigDecimal,
    /**
     * Represents collection of categories associated with amount.
     */
    val categoryAmounts: CategoryAmounts,
    val transactionType: PlainTransactionType
)