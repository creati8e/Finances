package serg.chuprin.finances.core.api.domain.gateway

import serg.chuprin.finances.core.api.domain.model.User

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
interface AuthorizationGateway {

    suspend fun isAuthorized(): Boolean

    suspend fun signIn(idToken: String): User?

}