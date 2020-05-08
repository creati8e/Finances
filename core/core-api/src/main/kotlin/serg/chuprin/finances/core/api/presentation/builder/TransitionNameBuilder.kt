package serg.chuprin.finances.core.api.presentation.builder

import serg.chuprin.finances.core.api.domain.model.Id

/**
 * Created by Sergey Chuprin on 08.05.2020.
 */
interface TransitionNameBuilder {

    fun buildForForMoneyAccountDetails(moneyAccountId: Id): String

}