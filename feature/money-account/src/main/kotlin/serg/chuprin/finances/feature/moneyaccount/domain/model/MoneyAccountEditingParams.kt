package serg.chuprin.finances.feature.moneyaccount.domain.model

import serg.chuprin.finances.core.api.domain.model.Id
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 22.01.2021.
 */
class MoneyAccountEditingParams(
    val moneyAccountId: Id,
    val newName: String,
    val newBalance: BigDecimal
) {

    operator fun component1() = moneyAccountId
    operator fun component2() = newName
    operator fun component3() = newBalance

}