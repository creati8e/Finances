package serg.chuprin.finances.core.api.domain.model

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
sealed class SignInResult {

    object Error : SignInResult()

    object Success : SignInResult()

}