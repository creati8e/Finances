package serg.chuprin.finances.feature.userprofile.presentation.model.store

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
sealed class UserProfileIntent {

    object ClickOnLogOutButton : UserProfileIntent()

    object ClickOnOnLogoutConfirmationButton : UserProfileIntent()

}