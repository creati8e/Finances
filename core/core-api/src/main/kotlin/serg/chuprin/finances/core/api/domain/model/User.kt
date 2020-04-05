package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
data class User(
    val id: Id,
    val email: String,
    val photoUrl: String,
    val displayName: String
)