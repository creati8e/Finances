package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import serg.chuprin.finances.feature.moneyaccount.details.domain.model.MoneyAccountDetails

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
sealed class MoneyAccountDetailsAction {

    class FormatDetails(
        val details: MoneyAccountDetails?
    ) : MoneyAccountDetailsAction()

    class ExecuteIntent(
        val intent: MoneyAccountDetailsIntent
    ) : MoneyAccountDetailsAction()

}