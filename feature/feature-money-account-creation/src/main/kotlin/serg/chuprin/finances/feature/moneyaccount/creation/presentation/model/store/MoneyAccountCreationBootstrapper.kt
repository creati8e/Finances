package serg.chuprin.finances.feature.moneyaccount.creation.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 01.06.2020.
 */
@ScreenScope
class MoneyAccountCreationBootstrapper @Inject constructor() :
    StoreBootstrapper<MoneyAccountCreationAction> {

    override fun invoke(): Flow<MoneyAccountCreationAction> {
        return emptyFlow()
    }

}