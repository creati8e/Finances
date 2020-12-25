package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.*
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
        getAccountDocumentById(account.id).set(mapper.mapToFieldsMap(account))
    }

    fun updateAccount(account: MoneyAccount) {
        getAccountDocumentById(account.id).set(mapper.mapToFieldsMap(account))
    }

    fun accountFlow(accountId: Id): Flow<DocumentSnapshot?> {
        return getAccountDocumentById(accountId).asFlow()
    }

    fun userAccountsFlow(userId: Id): Flow<List<DocumentSnapshot>> {
        return getUserAccountsCollection(userId)
            .asFlow()
            .map { querySnapshot -> querySnapshot.documents }
    }

    suspend fun deleteAccounts(accounts: List<MoneyAccount>) {
        val collection = getCollection()
        firestore
            .runBatch { writeBatch ->
                accounts.forEach { transaction ->
                    writeBatch.delete(collection.document(transaction.id.value))
                }
            }
            .awaitWithLogging {
                "Money accounts were deleted"
            }
    }

    private fun getUserAccountsCollection(userId: Id): Query {
        return getCollection().whereEqualTo(FIELD_OWNER_ID, userId.value)
    }

    private fun getAccountDocumentById(accountId: Id): DocumentReference {
        return getCollection().document(accountId.value)
    }

    private fun getCollection(): CollectionReference {
        return firestore.collection(COLLECTION_NAME)
    }

}