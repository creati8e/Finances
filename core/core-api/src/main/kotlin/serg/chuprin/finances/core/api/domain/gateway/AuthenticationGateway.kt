package serg.chuprin.finances.core.api.domain.gateway

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
interface AuthenticationGateway {

    suspend fun isAuthenticated(): Boolean

}