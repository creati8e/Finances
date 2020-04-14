package serg.chuprin.finances.core.api.domain.repository

import serg.chuprin.finances.core.api.domain.model.MoneyAccount

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
interface MoneyAccountRepository {

    fun createAccount(account: MoneyAccount)

}