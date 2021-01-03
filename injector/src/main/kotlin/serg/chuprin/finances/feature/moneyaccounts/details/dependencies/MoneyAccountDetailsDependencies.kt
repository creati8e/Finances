package serg.chuprin.finances.feature.moneyaccounts.details.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.TransactionsByDayGrouper
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountService
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.domain.usecase.MarkMoneyAccountAsFavoriteUseCase
import serg.chuprin.finances.core.api.presentation.builder.TransactionCellBuilder
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.navigation.MoneyAccountDetailsNavigation

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
interface MoneyAccountDetailsDependencies {

    val navigation: MoneyAccountDetailsNavigation

    val userRepository: UserRepository
    val moneyAccountRepository: MoneyAccountRepository

    val markMoneyAccountAsFavoriteUseCase: MarkMoneyAccountAsFavoriteUseCase

    val moneyAccountService: MoneyAccountService
    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService

    val transactionCellBuilder: TransactionCellBuilder
    val transactionsByDayGrouper: TransactionsByDayGrouper

    val amountFormatter: AmountFormatter
    val dateTimeFormatter: DateTimeFormatter
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface MoneyAccountDetailsDependenciesComponent : MoneyAccountDetailsDependencies