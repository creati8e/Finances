package serg.chuprin.finances.core.api.domain.gateway

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
interface AuthorizationGateway {

    suspend fun isAuthorized(): Boolean

    /**
     * @return [Boolean] wrapped in [Result] indicating whether user is new or not.
     */
    suspend fun signIn(idToken: String): Result<Boolean>

    suspend fun logOut()

}