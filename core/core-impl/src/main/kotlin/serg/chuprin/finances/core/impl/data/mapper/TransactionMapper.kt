package serg.chuprin.finances.core.impl.data.mapper

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.toObject
import serg.chuprin.finances.core.api.domain.model.Transaction
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
class TransactionMapper @Inject constructor() : ModelMapper<DocumentSnapshot, Transaction> {

    override fun invoke(snapshot: DocumentSnapshot): Transaction? {
        return snapshot.toObject<Transaction>()
    }

}