package serg.chuprin.finances.feature.transaction

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionCategoryRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.api.presentation.navigation.TransactionNavigation

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
interface TransactionDependencies {

    val amountParser: AmountParser
    val resourceManger: ResourceManger
    val amountFormatter: AmountFormatter

    val transactionNavigation: TransactionNavigation

    val userRepository: UserRepository
    val moneyAccountRepository: MoneyAccountRepository
    val categoryRepository: TransactionCategoryRepository

}

@Component(dependencies = [CoreDependenciesProvider::class])
interface TransactionDependenciesComponent : TransactionDependencies