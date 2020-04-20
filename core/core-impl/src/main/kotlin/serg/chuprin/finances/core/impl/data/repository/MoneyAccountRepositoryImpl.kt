package serg.chuprin.finances.core.impl.data.repository

import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.impl.data.datasource.firebase.FirebaseMoneyAccountDataSource
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
internal class MoneyAccountRepositoryImpl @Inject constructor(
    private val dataSource: FirebaseMoneyAccountDataSource
) : MoneyAccountRepository {

    override fun createAccount(account: MoneyAccount) = dataSource.createAccount(account)

}