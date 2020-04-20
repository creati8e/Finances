package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.FirebaseFirestore
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseMoneyAccountFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.mapper.FirebaseMoneyAccountMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
internal class FirebaseMoneyAccountDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val mapper: FirebaseMoneyAccountMapper
) {

    fun createAccount(account: MoneyAccount) {
        firestore
            .collection(COLLECTION_NAME)
            .document(account.id.value)
            .set(mapper.mapToFieldsMap(account))
    }

}