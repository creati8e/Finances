package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.domain.repository.TransactionRepository
import serg.chuprin.finances.core.api.domain.repository.UserRepository

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
interface CoreRepositoriesProvider {

    val userRepository: UserRepository

    val transactionRepository: TransactionRepository

}