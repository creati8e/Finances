package serg.chuprin.finances.core.api.domain.model.moneyaccount.query

import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 27.12.2020.
 */
class MoneyAccountsQuery(
    val ownerId: Id,
    val accountIds: Set<Id> = emptySet()
)