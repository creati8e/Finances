package serg.chuprin.finances.core.impl.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
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

    override fun createAccount(account: MoneyAccount) = dataSource.createAccount(account)

    override fun userAccountsFlow(userId: Id): Flow<List<MoneyAccount>> {
        return dataSource
            .userAccountsFlow(userId)
            .map { accounts ->
                accounts.mapNotNull(mapper::mapFromSnapshot)
            }
    }

}