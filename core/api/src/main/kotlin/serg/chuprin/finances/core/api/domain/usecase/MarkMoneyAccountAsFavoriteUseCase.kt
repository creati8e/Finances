package serg.chuprin.finances.core.api.domain.usecase

import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 09.05.2020.
 */
class MarkMoneyAccountAsFavoriteUseCase @Inject constructor(
    private val moneyAccountRepository: MoneyAccountRepository
) {

    fun execute(moneyAccount: MoneyAccount, markAsFavorite: Boolean) {
        val updatedAccount = moneyAccount.copy(isFavorite = markAsFavorite)
        moneyAccountRepository.createOrUpdateAccount(updatedAccount)
    }

}