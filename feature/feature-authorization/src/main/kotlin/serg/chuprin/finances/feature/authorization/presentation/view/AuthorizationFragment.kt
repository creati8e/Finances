package serg.chuprin.finances.feature.authorization.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.ajalt.timberkt.Timber
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.CommonStatusCodes
import kotlinx.android.synthetic.main.fragment_authorization.*
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.feature.authorization.R


/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
class AuthorizationFragment : BaseFragment(R.layout.fragment_authorization) {

    private val googleSignInObserver = GoogleSignInResultObserver(
        fragment = this,
        onError = ::handleGoogleSignInError,
        onSuccess = ::handleSuccessfulGoogleSignIn
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(googleSignInObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInWithGoogleButton.onClick(googleSignInObserver::signIn)
    }

    private fun handleGoogleSignInError(exception: ApiException) {
        val messageStringRes = if (exception.statusCode == CommonStatusCodes.NETWORK_ERROR) {
            R.string.error_no_network
        } else {
            R.string.authorization_sign_in_with_google_unknown_error
        }
        Toast.makeText(requireActivity(), messageStringRes, Toast.LENGTH_SHORT).show()
    }

    private fun handleSuccessfulGoogleSignIn() {
        // TODO: Navigate to Dashboard screen.
        Timber.d { "handleSuccessfulGoogleSignIn" }
    }

}