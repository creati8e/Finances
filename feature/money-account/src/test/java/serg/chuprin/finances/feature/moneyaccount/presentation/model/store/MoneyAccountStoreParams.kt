package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import serg.chuprin.finances.core.api.domain.MoneyAccountBalanceCalculator
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.feature.moneyaccount.domain.usecase.CreateMoneyAccountUseCase
import serg.chuprin.finances.feature.moneyaccount.domain.usecase.DeleteMoneyAccountUseCase
import serg.chuprin.finances.feature.moneyaccount.domain.usecase.EditMoneyAccountUseCase

/**
 * Created by Sergey Chuprin on 21.01.2021.
 */
class MoneyAccountStoreParams(
    val moneyAccountStore: MoneyAccountTestStore,
    val moneyAccountRepository: MoneyAccountRepository,
    val balanceCalculator: MoneyAccountBalanceCalculator,
    val editMoneyAccountUseCase: EditMoneyAccountUseCase,
    val deleteMoneyAccountUseCase: DeleteMoneyAccountUseCase,
    val createMoneyAccountUseCase: CreateMoneyAccountUseCase,
    val currencyChoiceStore: CurrencyChoiceTestStore
)