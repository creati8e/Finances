package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
sealed class BaseUser(
    open val id: Id,
    open val email: String,
    open val photoUrl: String,
    open val displayName: String
)

/**
 * This user model represents user without onboarding completed.
 */
data class IncompleteUser(
    override val id: Id,
    override val email: String,
    override val photoUrl: String,
    override val displayName: String
) : BaseUser(id, email, photoUrl, displayName) {

    companion object {
        fun create(
            id: String?,
            email: String?,
            photoUrl: String?,
            displayName: String?
        ): IncompleteUser? {
            if (id.isNullOrBlank()) return null
            if (email.isNullOrBlank()) return null
            return IncompleteUser(
                id = Id(id),
                email = email,
                photoUrl = photoUrl.orEmpty(),
                displayName = displayName.orEmpty()
            )
        }
    }

}

/**
 * Complete user model. Represents user with completed onboarding.
 */
data class User(
    override val id: Id,
    override val email: String,
    override val photoUrl: String,
    override val displayName: String,
    val defaultCurrencyCode: String
) : BaseUser(id, email, photoUrl, displayName) {

    companion object {
        fun create(
            id: String?,
            email: String?,
            photoUrl: String?,
            displayName: String?,
            defaultCurrencyCode: String?
        ): User? {
            if (id.isNullOrBlank()) return null
            if (email.isNullOrBlank()) return null
            if (defaultCurrencyCode.isNullOrEmpty()) return null
            return User(
                id = Id(id),
                email = email,
                photoUrl = photoUrl.orEmpty(),
                displayName = displayName.orEmpty(),
                defaultCurrencyCode = defaultCurrencyCode
            )
        }
    }
}