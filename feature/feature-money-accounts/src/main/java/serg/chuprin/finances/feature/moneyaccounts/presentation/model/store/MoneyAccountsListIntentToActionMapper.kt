package serg.chuprin.finances.feature.moneyaccounts.presentation.model.store

import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class MoneyAccountsListIntentToActionMapper :
    StoreIntentToActionMapper<MoneyAccountsListIntent, MoneyAccountsListAction> {

    override fun invoke(intent: MoneyAccountsListIntent): MoneyAccountsListAction {
        return MoneyAccountsListAction.ExecuteIntent(intent)
    }

}