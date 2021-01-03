package serg.chuprin.finances.feature.transaction

import dagger.Component
import serg.chuprin.finances.core.api.di.provider.CoreDependenciesProvider
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.presentation.formatter.AmountFormatter
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
interface TransactionDependencies {

    val amountParser: AmountParser
    val resourceManger: ResourceManger
    val amountFormatter: AmountFormatter

    val userRepository: UserRepository
    val moneyAccountRepository: MoneyAccountRepository

}

@Component(dependencies = [CoreDependenciesProvider::class])
interface TransactionDependenciesComponent : TransactionDependencies