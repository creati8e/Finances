package serg.chuprin.finances.feature.moneyaccounts.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccountToBalance
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import serg.chuprin.finances.core.api.domain.service.MoneyAccountBalanceService
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.05.2020.
 */
class GetUserMoneyAccountsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val moneyAccountBalanceService: MoneyAccountBalanceService
) {

    fun execute(): Flow<MoneyAccountToBalance> {
        return userRepository
            .currentUserSingleFlow()
            .flatMapLatest(moneyAccountBalanceService::balancesFlow)
    }

}