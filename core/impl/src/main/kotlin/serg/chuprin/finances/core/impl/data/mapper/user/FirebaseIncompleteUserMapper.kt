package serg.chuprin.finances.core.impl.data.mapper.user

import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.IncompleteUser
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_DISPLAY_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_EMAIL
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseUserFieldsContract.FIELD_PHOTO_URL
import serg.chuprin.finances.core.impl.data.mapper.FirebaseDataPeriodTypeMapper
import serg.chuprin.finances.core.impl.data.mapper.base.ModelMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
internal class FirebaseIncompleteUserMapper @Inject constructor(
    private val dataPeriodTypeMapper: FirebaseDataPeriodTypeMapper
) : ModelMapper<DocumentSnapshot, IncompleteUser> {

    override fun mapTo(model: DocumentSnapshot): IncompleteUser? {
        return IncompleteUser.create(
            id = model.id,
            email = model.getString(FIELD_EMAIL),
            photoUrl = model.getString(FIELD_PHOTO_URL),
            displayName = model.getString(FIELD_DISPLAY_NAME),
            dataPeriodType = dataPeriodTypeMapper.mapTo(
                model.getString(FirebaseUserFieldsContract.FIELD_DATA_PERIOD_TYPE).orEmpty()
            )
        )
    }

}