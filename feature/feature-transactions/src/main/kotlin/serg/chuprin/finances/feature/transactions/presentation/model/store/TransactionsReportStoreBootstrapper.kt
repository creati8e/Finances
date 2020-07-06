package serg.chuprin.finances.feature.transactions.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.api.domain.service.TransactionCategoryRetrieverService
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 12.05.2020.
 */
class TransactionsReportStoreBootstrapper @Inject constructor(
    private val transactionCategoryRetrieverService: TransactionCategoryRetrieverService
) : StoreBootstrapper<TransactionsReportAction> {

    override fun invoke(): Flow<TransactionsReportAction> {
        TODO("Not yet implemented")
    }

}