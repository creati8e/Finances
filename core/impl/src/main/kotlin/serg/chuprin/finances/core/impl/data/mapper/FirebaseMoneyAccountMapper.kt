package serg.chuprin.finances.core.impl.data.mapper

import com.google.firebase.firestore.DocumentSnapshot
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.extensions.nonNullValuesMap
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseMoneyAccountFieldsContract.FIELD_CURRENCY_CODE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseMoneyAccountFieldsContract.FIELD_IS_FAVORITE
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseMoneyAccountFieldsContract.FIELD_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseMoneyAccountFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.mapper.base.FirebaseModelMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal class FirebaseMoneyAccountMapper @Inject constructor() :
    FirebaseModelMapper<MoneyAccount> {

    override fun mapFromSnapshot(snapshot: DocumentSnapshot): MoneyAccount? {
        return MoneyAccount.create(
            id = snapshot.id,
            name = snapshot.getString(FIELD_NAME),
            ownerId = snapshot.getString(FIELD_OWNER_ID),
            isFavorite = snapshot.getBoolean(FIELD_IS_FAVORITE),
            currencyCode = snapshot.getString(FIELD_CURRENCY_CODE)
        )
    }

    override fun mapToFieldsMap(model: MoneyAccount): Map<String, Any> {
        return nonNullValuesMap(
            FIELD_NAME to model.name,
            FIELD_OWNER_ID to model.ownerId.value,
            FIELD_IS_FAVORITE to model.isFavorite,
            FIELD_CURRENCY_CODE to model.currencyCode
        )
    }

}