package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
data class MoneyAccount(
    val id: Id,
    val name: String,
    val ownerId: Id,
    val currencyCode: String
)