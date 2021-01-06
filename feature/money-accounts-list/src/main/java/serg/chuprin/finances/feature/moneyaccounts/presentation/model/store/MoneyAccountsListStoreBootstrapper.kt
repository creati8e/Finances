package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountsListScreenArguments
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.moneyaccounts.domain.usecase.GetUserMoneyAccountsUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class MoneyAccountsListStoreBootstrapper @Inject constructor(
    private val screenArguments: MoneyAccountsListScreenArguments,
    private val getUserMoneyAccountsUseCase: GetUserMoneyAccountsUseCase
) : StoreBootstrapper<MoneyAccountsListAction> {

    override fun invoke(): Flow<MoneyAccountsListAction> {
        return merge(
            flowOf(
                MoneyAccountsListAction.ChangeAccountCreationButtonVisibility(
                    isVisible = !screenArguments.isInPickerMode()
                )
            ),
            getUserMoneyAccountsUseCase
                .execute()
                .map { moneyAccounts ->
                    MoneyAccountsListAction.BuildMoneyAccountCells(moneyAccounts)
                }
        )
    }

}