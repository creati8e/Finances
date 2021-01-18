package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.domain.MoneyAccountBalanceCalculator
import serg.chuprin.finances.core.api.domain.TransactionAmountCalculator
import serg.chuprin.finances.core.api.domain.linker.TransactionWithCategoriesLinker
import serg.chuprin.finances.core.api.presentation.model.AppDebugMenu
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger

/**
 * Created by Sergey Chuprin on 12.04.2020.
 */
interface CoreManagerProvider {

    val appDebugMenu: AppDebugMenu

    val resourceManger: ResourceManger

    val transactionAmountCalculator: TransactionAmountCalculator

    val moneyAccountBalanceCalculator: MoneyAccountBalanceCalculator

    val transactionWithCategoriesLinker: TransactionWithCategoriesLinker

}