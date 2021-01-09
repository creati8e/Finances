package serg.chuprin.finances.feature.transaction.domain.usecase

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.01.2021.
 */
class DeleteTransactionUseCase @Inject constructor(
    private val transactionRepository: TransactionRepository
) {

    fun execute(transactionId: Id) {
        transactionRepository.deleteTransactions(setOf(transactionId))
    }

}