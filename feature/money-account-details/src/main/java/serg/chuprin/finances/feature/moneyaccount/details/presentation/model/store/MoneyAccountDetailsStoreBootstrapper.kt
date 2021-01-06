package serg.chuprin.finances.feature.moneyaccount.details.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.moneyaccount.details.domain.usecase.GetMoneyAccountDetailsUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 07.05.2020.
 */
class MoneyAccountDetailsStoreBootstrapper @Inject constructor(
    private val moneyAccountId: String,
    private val getMoneyAccountDetailsUseCase: GetMoneyAccountDetailsUseCase
) : StoreBootstrapper<MoneyAccountDetailsAction> {

    override fun invoke(): Flow<MoneyAccountDetailsAction> {
        return getMoneyAccountDetailsUseCase
            .execute(Id.existing(moneyAccountId))
            .map { moneyAccountDetails ->
                MoneyAccountDetailsAction.FormatDetails(moneyAccountDetails)
            }
    }

}