package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.impl.data.datasource.firebase.FirebaseMoneyAccountDataSource
import serg.chuprin.finances.core.impl.data.mapper.FirebaseMoneyAccountMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
internal class MoneyAccountRepositoryImpl @Inject constructor(
    private val mapper: FirebaseMoneyAccountMapper,
    private val dataSource: FirebaseMoneyAccountDataSource
) : MoneyAccountRepository {

    override fun createOrUpdateAccount(account: MoneyAccount) {
        dataSource.createOrUpdateAccount(account)
    }

    override fun deleteAccounts(accountIds: Collection<Id>) = dataSource.deleteAccounts(accountIds)

    override fun accountFlow(accountId: Id): Flow<MoneyAccount?> {
        return dataSource
            .accountFlow(accountId)
            .map { snapshot ->
                snapshot?.let(mapper::mapFromSnapshot)
            }
    }

    override fun accountsFlow(query: MoneyAccountsQuery): Flow<List<MoneyAccount>> {
        return dataSource
            .accountsFlow(query)
            .map { accounts ->
                accounts.mapNotNull(mapper::mapFromSnapshot)
            }
    }

}