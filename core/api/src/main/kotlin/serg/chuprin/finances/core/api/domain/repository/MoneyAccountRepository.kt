package serg.chuprin.finances.core.api.domain.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
interface MoneyAccountRepository {

    fun createOrUpdateAccount(account: MoneyAccount)

    fun accountFlow(accountId: Id): Flow<MoneyAccount?>

    fun accountsFlow(query: MoneyAccountsQuery): Flow<List<MoneyAccount>>

    fun deleteAccounts(accountIds: Collection<Id>)

    suspend fun accounts(query: MoneyAccountsQuery): List<MoneyAccount> {
        return accountsFlow(query).firstOrNull().orEmpty()
    }

}