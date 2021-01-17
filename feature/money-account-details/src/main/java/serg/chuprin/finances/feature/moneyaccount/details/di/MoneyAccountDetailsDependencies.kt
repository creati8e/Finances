package serg.chuprin.finances.feature.moneyaccount.details.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountBalanceService
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.domain.usecase.MarkMoneyAccountAsFavoriteUseCase
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.feature.moneyaccount.details.presentation.MoneyAccountDetailsNavigation

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
interface MoneyAccountDetailsDependencies : FeatureDependencies {

    val resourceManger: ResourceManger

    val navigation: MoneyAccountDetailsNavigation

    val userRepository: UserRepository
    val moneyAccountRepository: MoneyAccountRepository

    val markMoneyAccountAsFavoriteUseCase: MarkMoneyAccountAsFavoriteUseCase

    val moneyAccountBalanceService: MoneyAccountBalanceService
    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService

    val transactionCellBuilder: TransactionCellBuilder
    val transactionsByDayGrouper: TransactionsByDayGrouper
    val transactionAmountCalculator: TransactionAmountCalculator

    val amountFormatter: AmountFormatter
    val dateTimeFormatter: DateTimeFormatter
}