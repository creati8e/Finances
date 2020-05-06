package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.moneyaccounts.domain.usecase.GetUserMoneyAccountsUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class MoneyAccountsListStoreBootstrapper @Inject constructor(
    private val getUserMoneyAccountsUseCase: GetUserMoneyAccountsUseCase
) : StoreBootstrapper<MoneyAccountsListAction> {

    override fun invoke(): Flow<MoneyAccountsListAction> {
        return getUserMoneyAccountsUseCase
            .execute()
            .map { moneyAccounts ->
                MoneyAccountsListAction.BuildMoneyAccountCells(moneyAccounts)
            }
    }

}