package serg.chuprin.finances.feature.transaction.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import java.time.LocalDate
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.01.2021.
 */
class TransactionStoreBootstrapper @Inject constructor() : StoreBootstrapper<TransactionAction> {

    override fun invoke(): Flow<TransactionAction> {
        return flowOfSingleValue {
            TransactionAction.FormatInitialState(
                category = null,
                date = LocalDate.now()
            )
        }
    }

}