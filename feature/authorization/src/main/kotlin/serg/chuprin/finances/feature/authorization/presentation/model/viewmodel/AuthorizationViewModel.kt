package serg.chuprin.finances.feature.authorization.presentation.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.domain.model.SignInResult
import serg.chuprin.finances.feature.authorization.domain.usecase.SignInUseCase
import serg.chuprin.finances.feature.authorization.presentation.model.SignInState
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
@ScreenScope
class AuthorizationViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    val signInStateLiveData: LiveData<SignInState>
        get() = signInStateMutableLiveData

    private val signInStateMutableLiveData = MutableLiveData<SignInState>()

    fun signIn(idToken: String) {
        viewModelScope.launch {
            signInStateMutableLiveData.value = SignInState.Progress

            val signInResult = signInUseCase.execute(idToken)
            signInStateMutableLiveData.value = when (signInResult) {
                SignInResult.Error -> SignInState.Error
                is SignInResult.Success -> SignInState.Success
            }
        }
    }

}