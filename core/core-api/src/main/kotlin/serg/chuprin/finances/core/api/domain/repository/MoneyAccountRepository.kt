package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.MoneyAccount

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
interface MoneyAccountRepository {

    fun updateAccount(account: MoneyAccount)

    fun createAccount(account: MoneyAccount)

    fun accountFlow(accountId: Id): Flow<MoneyAccount?>

    fun userAccountsFlow(userId: Id): Flow<List<MoneyAccount>>

    suspend fun getUserAccounts(userId: Id): List<MoneyAccount>

}