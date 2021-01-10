package serg.chuprin.finances.core.impl.data.datasource.firebase

import com.google.firebase.firestore.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.moneyaccount.MoneyAccount
import serg.chuprin.finances.core.api.domain.model.moneyaccount.query.MoneyAccountsQuery
import serg.chuprin.finances.core.api.extensions.contains
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

    fun createOrUpdateAccount(account: MoneyAccount) {
        getAccountDocumentById(account.id).set(mapper.mapToFieldsMap(account))
    }

    fun accountFlow(accountId: Id): Flow<DocumentSnapshot?> {
        return getAccountDocumentById(accountId).asFlow()
    }

    fun accountsFlow(query: MoneyAccountsQuery): Flow<List<DocumentSnapshot>> {
        return collection
            .filterByOwnerId(query.ownerId)
            .asFlow()
            .map { querySnapshot ->
                querySnapshot.filterByAccountIds(query.accountIds)
            }
    }

    fun deleteAccounts(accountIds: Collection<Id>) = delete(accountIds)

    private fun CollectionReference.filterByOwnerId(ownerId: Id): Query {
        return whereEqualTo(FIELD_OWNER_ID, ownerId.value)
    }

    private fun QuerySnapshot.filterByAccountIds(accountIds: Set<Id>): List<DocumentSnapshot> {
        if (accountIds.isEmpty()) {
            return documents
        }
        return documents.filter { document ->
            accountIds.contains { accountId -> accountId.value == document.id }
        }
    }

    private fun getAccountDocumentById(accountId: Id): DocumentReference {
        return collection.document(accountId.value)
    }

}