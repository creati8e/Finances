package serg.chuprin.finances.feature.transactions.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import serg.chuprin.finances.feature.transactions.domain.usecase.BuildTransactionsReportUseCase
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportStoreBootstrapper @Inject constructor(
    private val buildTransactionsReportUseCase: BuildTransactionsReportUseCase
) : StoreBootstrapper<TransactionsReportAction> {

    override fun invoke(): Flow<TransactionsReportAction> {
        return flow {
            emitAll(
                buildTransactionsReportUseCase
                    .execute()
                    .map(TransactionsReportAction::FormatReport)
            )
        }
    }

}