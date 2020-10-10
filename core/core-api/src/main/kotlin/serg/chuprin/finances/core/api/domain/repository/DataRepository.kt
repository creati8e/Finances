package serg.chuprin.finances.core.api.domain.repository

/**
 * Created by Sergey Chuprin on 31.07.2020.
 *
 * Represents common 'data' repository for all types of data.
 */
interface DataRepository {

    /**
     * Clear all saved data.
     */
    suspend fun clear()

}