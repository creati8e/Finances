package serg.chuprin.finances.feature.transaction.presentation.model

import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount

/**
 * Created by Sergey Chuprin on 03.01.2021.
 */
data class TransactionChosenMoneyAccount(
    val formattedName: String,
    val account: MoneyAccount
)