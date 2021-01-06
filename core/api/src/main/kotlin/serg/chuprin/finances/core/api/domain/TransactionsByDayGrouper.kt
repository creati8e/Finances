package serg.chuprin.finances.core.api.domain

import serg.chuprin.finances.core.api.domain.model.TransactionCategoriesMap
import serg.chuprin.finances.core.api.domain.model.TransactionsGroupedByDay
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.07.2020.
 *
 * This class groups transactions by day descending and sorts them from newest to oldest datetime in day frame.
 */
class TransactionsByDayGrouper @Inject constructor() {

    fun group(
        transactionCategories: TransactionCategoriesMap
    ): TransactionsGroupedByDay {
        return transactionCategories.entries
            .groupBy { (transaction) ->
                transaction.dateTime.toLocalDate()
            }
            .mapValues { (_, transactionAndCategoriesList) ->
                transactionAndCategoriesList.sortedByDescending { (transaction) ->
                    transaction.dateTime
                }
            }
            .toSortedMap(compareByDescending { localDate -> localDate })
    }

}