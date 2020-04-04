package serg.chuprin.finances.feature.dashboard.domain.usecase

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Transaction
import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
class ObserveLastUserTransactionsUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val transactionRepository: TransactionRepository
) {

    @OptIn(FlowPreview::class)
    fun execute(): Flow<List<Transaction>> {
        return userRepository
            .currentUserSingleFlow()
            .flatMapConcat { user ->
                transactionRepository
                    .observeUserTransactions(user.id)
                    .map { transactions -> transactions.take(5) }
            }
    }

}