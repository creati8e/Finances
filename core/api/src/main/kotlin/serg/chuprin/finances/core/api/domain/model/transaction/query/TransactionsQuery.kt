package serg.chuprin.finances.core.api.domain.model.transaction.query

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
class TransactionsQuery(
    val ownerId: Id? = null,
    val limit: Int? = null,
    val sortOrder: SortOrder? = null,
    val endDate: LocalDateTime? = null,
    val startDate: LocalDateTime? = null,
    val categoryIds: Set<Id?> = emptySet(),
    val transactionIds: Set<Id> = emptySet(),
    val moneyAccountIds: Set<Id> = emptySet(),
    val transactionType: PlainTransactionType? = null
) {

    enum class SortOrder { DATE_DESC, DATE_ASC }

}