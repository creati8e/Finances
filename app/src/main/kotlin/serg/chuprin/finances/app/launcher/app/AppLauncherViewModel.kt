package serg.chuprin.finances.app.launcher.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@ScreenScope
class AppLauncherViewModel @Inject constructor(
    private val authorizationGateway: AuthorizationGateway
) : ViewModel() {

    val isUserAuthorizedLiveData = liveData {
        emit(authorizationGateway.isAuthorized())
    }

}