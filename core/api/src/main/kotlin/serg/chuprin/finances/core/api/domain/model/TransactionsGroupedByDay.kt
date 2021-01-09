package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.category.CategoryWithParent
import serg.chuprin.finances.core.api.domain.model.transaction.Transaction
import java.time.LocalDate
import java.util.*

/**
 * Created by Sergey Chuprin on 06.07.2020.
 */
typealias TransactionsGroupedByDay = SortedMap<LocalDate, List<Map.Entry<Transaction, CategoryWithParent?>>>