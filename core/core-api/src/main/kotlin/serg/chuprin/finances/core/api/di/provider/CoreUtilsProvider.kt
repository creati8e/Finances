package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
interface CoreUtilsProvider {

    val amountParser: AmountParser

    val transactionsByDayGrouper: TransactionsByDayGrouper

}