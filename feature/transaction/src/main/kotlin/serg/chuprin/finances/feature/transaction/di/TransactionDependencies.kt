package serg.chuprin.finances.feature.transaction.di

import serg.chuprin.finances.core.api.di.dependencies.FeatureDependencies
import serg.chuprin.finances.core.api.domain.repository.CategoryRepository
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.formatter.CategoryWithParentFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.feature.transaction.presentation.TransactionNavigation

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
interface TransactionDependencies : FeatureDependencies {

    val amountParser: AmountParser
    val resourceManger: ResourceManger
    val amountFormatter: AmountFormatter
    val categoryNameFormatter: CategoryWithParentFormatter

    val transactionNavigation: TransactionNavigation

    val userRepository: UserRepository
    val transactionRepository: TransactionRepository
    val moneyAccountRepository: MoneyAccountRepository
    val categoryRepository: CategoryRepository

}