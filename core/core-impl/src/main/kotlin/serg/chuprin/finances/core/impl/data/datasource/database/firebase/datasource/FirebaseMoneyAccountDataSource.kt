package serg.chuprin.finances.core.impl.data.datasource.database.firebase.datasource

import com.google.firebase.firestore.FirebaseFirestore
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseMoneyAccountFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseMoneyAccountFieldsContract.FIELD_CURRENCY_CODE
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseMoneyAccountFieldsContract.FIELD_NAME
import serg.chuprin.finances.core.impl.data.datasource.database.firebase.contract.FirebaseMoneyAccountFieldsContract.FIELD_OWNER_ID
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
internal class FirebaseMoneyAccountDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun createAccount(account: MoneyAccount) {
        firestore
            .collection(COLLECTION_NAME)
            .document(account.id.value)
            .set(account.toMap())
    }

    private fun MoneyAccount.toMap(): Map<String, Any> {
        return mapOf(
            FIELD_NAME to name,
            FIELD_OWNER_ID to ownerId.value,
            FIELD_CURRENCY_CODE to currencyCode
        )
    }

}