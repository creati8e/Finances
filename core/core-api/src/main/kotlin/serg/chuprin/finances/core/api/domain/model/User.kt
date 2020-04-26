package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.extensions.EMPTY_STRING
import java.util.*

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
sealed class BaseUser(
    open val id: Id,
    open val email: String,
    open val photoUrl: String,
    open val displayName: String,
    open val dataPeriodType: DataPeriodType
)

/**
 * This user model represents user without onboarding completed.
 */
data class IncompleteUser(
    override val id: Id,
    override val email: String,
    override val photoUrl: String,
    override val displayName: String,
    override val dataPeriodType: DataPeriodType
) : BaseUser(id, email, photoUrl, displayName, dataPeriodType) {

    companion object {

        fun create(
            id: String?,
            email: String?,
            photoUrl: String?,
            displayName: String?,
            dataPeriodType: DataPeriodType?
        ): IncompleteUser? {
            if (id.isNullOrBlank()) return null
            if (id.isNullOrBlank()) return null
            if (dataPeriodType == null) return null
            if (email.isNullOrBlank()) return null
            return IncompleteUser(
                email = email,
                id = Id.existing(id),
                photoUrl = photoUrl.orEmpty(),
                dataPeriodType = dataPeriodType,
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
    override val dataPeriodType: DataPeriodType,
    val defaultCurrencyCode: String
) : BaseUser(id, email, photoUrl, displayName, dataPeriodType) {

    companion object {

        val EMPTY = User(
            Id(EMPTY_STRING),
            EMPTY_STRING,
            EMPTY_STRING,
            EMPTY_STRING,
            DataPeriodType.MONTH,
            EMPTY_STRING
        )

        fun create(
            id: String?,
            email: String?,
            photoUrl: String?,
            dataPeriodType: DataPeriodType?,
            displayName: String?,
            defaultCurrencyCode: String?
        ): User? {
            if (dataPeriodType == null) return null
            if (id.isNullOrBlank()) return null
            if (email.isNullOrBlank()) return null
            if (defaultCurrencyCode.isNullOrEmpty()) return null
            return User(
                email = email,
                id = Id.existing(id),
                dataPeriodType = dataPeriodType,
                photoUrl = photoUrl.orEmpty(),
                displayName = displayName.orEmpty(),
                defaultCurrencyCode = defaultCurrencyCode
            )
        }

    }

    val defaultCurrency: Currency
        get() = Currency.getInstance(defaultCurrencyCode)

}