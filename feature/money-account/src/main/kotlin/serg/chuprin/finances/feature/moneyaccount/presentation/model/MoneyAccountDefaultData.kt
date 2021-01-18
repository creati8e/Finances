package serg.chuprin.finances.feature.moneyaccount.presentation.model

import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 17.01.2021.
 */
data class MoneyAccountDefaultData(
    val balance: BigDecimal,
    val accountName: String
)