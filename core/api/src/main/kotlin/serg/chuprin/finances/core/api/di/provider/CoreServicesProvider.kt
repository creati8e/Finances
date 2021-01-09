package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.domain.service.MoneyAccountBalanceService
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
interface CoreServicesProvider {

    val moneyAccountBalanceService: MoneyAccountBalanceService

    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService

}