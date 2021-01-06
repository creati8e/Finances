package serg.chuprin.finances.core.api.domain.service

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccountBalances

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
interface MoneyAccountService {

    fun moneyAccountBalancesFlow(user: User): Flow<MoneyAccountBalances>

}