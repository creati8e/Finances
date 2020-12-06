package serg.chuprin.finances.feature.authorization.presentation.model

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
sealed class SignInState {

    object Error : SignInState()

    object Progress : SignInState()

    object Success : SignInState()

}