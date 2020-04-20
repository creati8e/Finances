package serg.chuprin.finances.core.impl.data.mapper.base

import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal interface FirebaseModelMapper<D> {

    fun mapToFieldsMap(model: D): Map<String, Any>

    fun mapFromSnapshot(snapshot: DocumentSnapshot): D?

}