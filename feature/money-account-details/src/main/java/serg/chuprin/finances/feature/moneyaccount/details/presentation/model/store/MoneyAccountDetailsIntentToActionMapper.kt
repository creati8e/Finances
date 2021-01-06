package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.core.mvi.mapper.StoreIntentToActionMapper

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsIntentToActionMapper :
    StoreIntentToActionMapper<MoneyAccountDetailsIntent, MoneyAccountDetailsAction> {

    override fun invoke(intent: MoneyAccountDetailsIntent): MoneyAccountDetailsAction {
        return MoneyAccountDetailsAction.ExecuteIntent(intent)
    }

}