package serg.chuprin.finances.feature.moneyaccount.presentation.model.store

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import serg.chuprin.finances.core.api.domain.MoneyAccountBalanceCalculator
import serg.chuprin.finances.core.api.domain.repository.MoneyAccountRepository
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.api.presentation.model.parser.AmountParser
import serg.chuprin.finances.core.api.presentation.screen.arguments.MoneyAccountScreenArguments
import serg.chuprin.finances.feature.moneyaccount.domain.usecase.CreateMoneyAccountUseCase
import serg.chuprin.finances.feature.moneyaccount.domain.usecase.DeleteMoneyAccountUseCase
import serg.chuprin.finances.feature.moneyaccount.domain.usecase.EditMoneyAccountUseCase
import java.math.BigDecimal

object MoneyAccountStoreCreator {

    fun create(screenArguments: MoneyAccountScreenArguments): MoneyAccountStoreParams {
        val amountParser = mockk<AmountParser>().apply {
            val amountSlot = slot<String>()
            every { parse(capture(amountSlot)) } answers {
                runCatching { BigDecimal(amountSlot.captured) }.getOrNull()
            }
        }
        val resourceManager = mockk<ResourceManger>().apply {
            every { getString(any()) } returns "string"
        }

        val moneyAccountRepository = mockk<MoneyAccountRepository>()

        val balanceCalculator = mockk<MoneyAccountBalanceCalculator>()

        val editMoneyAccountUseCase = mockk<EditMoneyAccountUseCase>()
        val deleteMoneyAccountUseCase = mockk<DeleteMoneyAccountUseCase>()
        val createMoneyAccountUseCase = mockk<CreateMoneyAccountUseCase>()

        val currencyChoiceStore =
            CurrencyChoiceTestStoreFactory.create(screenArguments, moneyAccountRepository)

        val moneyAccountStore = MoneyAccountTestStoreFactory(
            bootstrapper = MoneyAccountStoreBootstrapper(
                resourceManger = resourceManager,
                screenArguments = screenArguments,
                moneyAccountRepository = moneyAccountRepository,
                moneyAccountBalanceCalculator = balanceCalculator
            ),
            actionExecutor = MoneyAccountActionExecutor(
                amountParser,
                resourceManager,
                currencyChoiceStore,
                screenArguments,
                editMoneyAccountUseCase,
                createMoneyAccountUseCase,
                deleteMoneyAccountUseCase
            ),
            currencyChoiceStore = currencyChoiceStore
        ).create()

        return MoneyAccountStoreParams(
            moneyAccountStore,
            moneyAccountRepository,
            balanceCalculator,
            editMoneyAccountUseCase,
            deleteMoneyAccountUseCase,
            createMoneyAccountUseCase,
            currencyChoiceStore
        )

    }

}