package serg.chuprin.finances.core.api.domain.model

import java.util.*

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
data class Transaction(
    val id: Id,
    val date: Date,
    val userId: Id,
    val amount: String
)