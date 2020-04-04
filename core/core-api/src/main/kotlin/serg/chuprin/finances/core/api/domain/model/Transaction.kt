package serg.chuprin.finances.core.api.domain.model

import java.util.*

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
data class Transaction(
    val uuid: UUID,
    val date: Date,
    val amount: String
)