package serg.chuprin.finances.feature.userprofile.presentation.model.store

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
sealed class UserProfileEvent {

    object NavigateToLoginScreen : UserProfileEvent()

    object ShowLogoutConfirmDialog : UserProfileEvent()

}