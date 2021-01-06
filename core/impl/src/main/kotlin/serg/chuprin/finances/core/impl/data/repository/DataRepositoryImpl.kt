package serg.chuprin.finances.core.impl.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import serg.chuprin.finances.core.api.domain.repository.DataRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
internal class DataRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : DataRepository {

    override suspend fun clear() {
        firestore.terminate().await()
        firestore.clearPersistence().await()
    }

}