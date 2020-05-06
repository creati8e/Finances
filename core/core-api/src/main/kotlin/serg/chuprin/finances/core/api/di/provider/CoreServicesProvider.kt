package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.domain.service.MoneyAccountService
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
interface CoreServicesProvider {

    val moneyAccountService: MoneyAccountService

    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService

}