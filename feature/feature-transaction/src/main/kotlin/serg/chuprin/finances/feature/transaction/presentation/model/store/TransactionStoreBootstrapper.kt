package serg.chuprin.finances.feature.transaction.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import java.time.LocalDate
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionStoreBootstrapper @Inject constructor(
    private val userRepository: UserRepository,
    private val moneyAccountRepository: MoneyAccountRepository
) : StoreBootstrapper<TransactionAction> {

    override fun invoke(): Flow<TransactionAction> {
        return flowOfSingleValue {
            TransactionAction.FormatInitialState(
                category = null,
                date = LocalDate.now(),
                moneyAccount = getFirstFavoriteMoneyAccount()
            )
        }
    }

    private suspend fun getFirstFavoriteMoneyAccount(): MoneyAccount {
        val moneyAccounts = moneyAccountRepository
            .accountsFlow(MoneyAccountsQuery(userRepository.getCurrentUser().id))
            .first()
            .sortedBy(MoneyAccount::name)

        return moneyAccounts
            .firstOrNull(MoneyAccount::isFavorite)
            ?: moneyAccounts.first()
    }

}