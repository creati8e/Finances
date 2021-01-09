package serg.chuprin.finances.core.api.extensions

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
val Collection<Transaction>.categoryIds: Set<Id>
    get() = mapNotNullTo(mutableSetOf(), Transaction::categoryId)