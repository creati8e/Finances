package serg.chuprin.finances.feature.moneyaccount.domain.model

import java.math.BigDecimal
import java.util.*

/**
 * Created by Sergey Chuprin on 21.01.2021.
 */
class MoneyAccountCreationParams(
    val currency: Currency,
    val accountName: String,
    val initialBalance: BigDecimal
)