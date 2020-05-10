package serg.chuprin.finances.feature.moneyaccounts.details.dependencies

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountService
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.api.domain.usecase.MarkMoneyAccountAsFavoriteUseCase
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryColorFormatter
import serg.chuprin.finances.core.api.presentation.formatter.DateTimeFormatter
import serg.chuprin.finances.core.api.presentation.formatter.TransactionCategoryWithParentFormatter

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
interface MoneyAccountDetailsDependencies {

    val moneyAccountRepository: MoneyAccountRepository

    val markMoneyAccountAsFavoriteUseCase: MarkMoneyAccountAsFavoriteUseCase

    val moneyAccountService: MoneyAccountService
    val transactionCategoryRetrieverService: TransactionCategoryRetrieverService

    val amountFormatter: AmountFormatter
    val dateTimeFormatter: DateTimeFormatter
    val categoryColorFormatter: CategoryColorFormatter
    val transactionCategoryWithParentFormatter: TransactionCategoryWithParentFormatter
}

@Component(dependencies = [CoreDependenciesProvider::class])
internal interface MoneyAccountDetailsDependenciesComponent : MoneyAccountDetailsDependencies