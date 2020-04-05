package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@Suppress("DataClassPrivateConstructor")
data class User private constructor(
    val id: Id,
    val email: String,
    val photoUrl: String,
    val displayName: String
) {

    companion object {

        fun create(
            id: String?,
            email: String?,
            photoUrl: String?,
            displayName: String?
        ): User? {
            if (id.isNullOrBlank()) return null
            if (email.isNullOrBlank()) return null
            return User(
                id = Id(id),
                email = email,
                photoUrl = photoUrl.orEmpty(),
                displayName = displayName.orEmpty()
            )
        }

    }

}