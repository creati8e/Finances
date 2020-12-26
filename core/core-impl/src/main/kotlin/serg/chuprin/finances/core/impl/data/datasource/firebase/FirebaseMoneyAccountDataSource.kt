package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    firestore: FirebaseFirestore,
    private val mapper: FirebaseMoneyAccountMapper
) : BaseFirebaseDataSource(firestore) {

    override val collection: CollectionReference
        get() = firestore.collection(COLLECTION_NAME)

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
        delete(accounts.map(MoneyAccount::id))
    }

    private fun getUserAccountsCollection(userId: Id): Query {
        return collection.whereEqualTo(FIELD_OWNER_ID, userId.value)
    }

    private fun getAccountDocumentById(accountId: Id): DocumentReference {
        return collection.document(accountId.value)
    }

}