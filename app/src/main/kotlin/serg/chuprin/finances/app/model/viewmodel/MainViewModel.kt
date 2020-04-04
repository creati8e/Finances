package serg.chuprin.finances.app.model.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.gateway.AuthenticationGateway
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@ScreenScope
class MainViewModel @Inject constructor(
    private val authenticationGateway: AuthenticationGateway
) : ViewModel() {

    val userAuthorizedLiveData = liveData {
        emit(authenticationGateway.isAuthenticated())
    }

}