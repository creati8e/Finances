package serg.chuprin.finances.core.impl.data.mapper.user

import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.User
import serg.chuprin.finances.core.api.extensions.nonNullValuesMap
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_DATA_PERIOD_TYPE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_DEFAULT_CURRENCY_CODE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_DISPLAY_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_EMAIL
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_PHOTO_URL
import serg.chuprin.finances.core.impl.data.mapper.FirebaseDataPeriodTypeMapper
import serg.chuprin.finances.core.impl.data.mapper.base.FirebaseModelMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
internal class FirebaseUserMapper @Inject constructor(
    private val dataPeriodTypeMapper: FirebaseDataPeriodTypeMapper
) : FirebaseModelMapper<User> {

    override fun mapFromSnapshot(snapshot: DocumentSnapshot): User? {
        return User.create(
            id = snapshot.id,
            email = snapshot.getString(FIELD_EMAIL),
            photoUrl = snapshot.getString(FIELD_PHOTO_URL),
            displayName = snapshot.getString(FIELD_DISPLAY_NAME),
            dataPeriodType = dataPeriodTypeMapper.mapTo(
                snapshot.getString(FIELD_DATA_PERIOD_TYPE).orEmpty()
            ),
            defaultCurrencyCode = snapshot.getString(FIELD_DEFAULT_CURRENCY_CODE)
        )
    }

    override fun mapToFieldsMap(model: User): Map<String, Any> {
        return nonNullValuesMap(
            FIELD_EMAIL to model.email,
            FIELD_PHOTO_URL to model.photoUrl,
            FIELD_DISPLAY_NAME to model.displayName,
            FIELD_DEFAULT_CURRENCY_CODE to model.defaultCurrencyCode,
            FIELD_DATA_PERIOD_TYPE to dataPeriodTypeMapper.mapFrom(model.dataPeriodType)
        )
    }

}