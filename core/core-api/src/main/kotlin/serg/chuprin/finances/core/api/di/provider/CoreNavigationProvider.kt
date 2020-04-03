package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.presentation.navigation.AuthorizationNavigation

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
interface CoreNavigationProvider {

    fun authorizationNavigation(): AuthorizationNavigation

}