package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.MoneyAccount
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseMoneyAccountFieldsContract.COLLECTION_NAME
import serg.chuprin.finances.core.impl.data.datasource.firebase.contract.FirebaseMoneyAccountFieldsContract.FIELD_OWNER_ID
import serg.chuprin.finances.core.impl.data.mapper.FirebaseMoneyAccountMapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 13.04.2020.
 */
internal class FirebaseMoneyAccountDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val mapper: FirebaseMoneyAccountMapper
) {

    suspend fun getUserAccounts(userId: Id): List<DocumentSnapshot> {
        return getUserAccountsCollection(userId).get().await()?.documents.orEmpty()
    }

    fun createAccount(account: MoneyAccount) {
        getCollection()
            .document(account.id.value)
            .set(mapper.mapToFieldsMap(account))
    }

    fun userAccountsFlow(userId: Id): Flow<List<DocumentSnapshot>> {
        return getUserAccountsCollection(userId)
            .asFlow()
            .map { querySnapshot -> querySnapshot.documents }
    }

    private fun getUserAccountsCollection(userId: Id): Query {
        return getCollection().whereEqualTo(FIELD_OWNER_ID, userId.value)
    }

    private fun getCollection(): CollectionReference {
        return firestore.collection(COLLECTION_NAME)
    }

}