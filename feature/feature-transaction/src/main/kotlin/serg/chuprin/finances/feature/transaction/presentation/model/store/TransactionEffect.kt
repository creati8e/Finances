package serg.chuprin.finances.feature.transaction.presentation.model.store

import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenCategory
import serg.chuprin.finances.feature.transaction.presentation.model.TransactionChosenDate

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
sealed class TransactionEffect {

    class DateChanged(
        val chosenDate: TransactionChosenDate
    ) : TransactionEffect()

    class CategoryChanged(
        val chosenCategory: TransactionChosenCategory
    ) : TransactionEffect()

}