package serg.chuprin.finances.feature.authorization.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_authorization.*
import serg.chuprin.finances.core.api.di.dependencies.findComponentDependencies
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.feature.authorization.presentation.AuthorizationNavigation
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.shortToast
import serg.chuprin.finances.core.api.presentation.view.extensions.makeGone
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisible
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.feature.authorization.R
import serg.chuprin.finances.feature.authorization.di.AuthorizationComponent
import serg.chuprin.finances.feature.authorization.presentation.model.SignInState
import javax.inject.Inject


/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
class AuthorizationFragment : BaseFragment(R.layout.fragment_authorization) {

    @Inject
    lateinit var navigation: AuthorizationNavigation

    private val googleSignInObserver = GoogleSignInResultObserver(
        fragment = this,
        onError = ::handleGoogleSignInError,
        onSuccess = { idToken -> viewModel.signIn(idToken) }
    )

    private val viewModel by viewModelFromComponent { component }

    private val component by component { AuthorizationComponent.get(findComponentDependencies()) }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(googleSignInObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edgeToEdge {
            view.fit { Edge.All }
        }
        signInWithGoogleButton.onClick(googleSignInObserver::signIn)
        viewModel.signInStateLiveData(::handleSignInState)
    }

    private fun handleSignInState(signInState: SignInState) {
        return when (signInState) {
            is SignInState.Success -> {
                shortToast(R.string.authorization_successful_sign_in)
                navigation.navigateToAuthorizedGraph(requireParentFragment().findNavController())
            }
            SignInState.Error -> {
                progressBar.makeGone()
                signInWithGoogleButton.isEnabled = true
                shortToast(R.string.authorization_sign_in_with_google_unknown_error)
            }
            SignInState.Progress -> {
                progressBar.makeVisible()
                signInWithGoogleButton.isEnabled = false
            }
        }
    }

    private fun handleGoogleSignInError(exception: ApiException) {
        val messageStringRes = if (exception.statusCode == CommonStatusCodes.NETWORK_ERROR) {
            R.string.error_no_network
        } else {
            R.string.authorization_sign_in_with_google_unknown_error
        }
        shortToast(messageStringRes)
    }

}