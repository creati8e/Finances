package serg.chuprin.finances.feature.userprofile.presentation.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_profile.*
import serg.chuprin.finances.core.api.presentation.extensions.setupToolbar
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.component
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.navigation.UserProfileNavigation
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.decoration.CellDividerDecoration
import serg.chuprin.finances.core.api.presentation.view.dialog.info.InfoDialogFragment
import serg.chuprin.finances.core.api.presentation.view.dialog.info.showInfoDialog
import serg.chuprin.finances.core.api.presentation.view.setEnterSharedElementTransition
import serg.chuprin.finances.feature.userprofile.R
import serg.chuprin.finances.feature.userprofile.presentation.di.UserProfileComponent
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileLogoutCell
import serg.chuprin.finances.feature.userprofile.presentation.model.store.UserProfileEvent
import serg.chuprin.finances.feature.userprofile.presentation.model.store.UserProfileIntent
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.UserProfileCellsListAdapter
import javax.inject.Inject
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 25.07.2020.
 */
class UserProfileFragment :
    BaseFragment(R.layout.fragment_user_profile),
    InfoDialogFragment.Callback {

    private companion object {
        private const val RC_DIALOG_LOGOUT = 10001
    }

    @Inject
    lateinit var navigation: UserProfileNavigation

    private val viewModel by viewModelFromComponent { component }

    private val component by component { UserProfileComponent.get() }

    private val cellsAdapter = UserProfileCellsListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEnterSharedElementTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }
        with(recyclerView) {
            adapter = cellsAdapter.apply {
                clickListener = { cell, _, _ ->
                    if (cell is UserProfileLogoutCell) {
                        viewModel.dispatchIntent(UserProfileIntent.ClickOnLogOutButton)
                    }
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(
                CellDividerDecoration(
                    requireContext(),
                    cellsAdapter,
                    marginEndDp = -8,
                    marginStartDp = 8
                )
            )
        }
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        with(viewModel) {
            eventsLiveData(::handleEvent)
            cellsLiveData(cellsAdapter::setItems)
        }
    }

    override fun onInfoDialogPositiveButtonClick(requestCode: Int) {
        if (requestCode == RC_DIALOG_LOGOUT) {
            viewModel.dispatchIntent(UserProfileIntent.ClickOnOnLogoutConfirmationButton)
        }
    }

    private fun handleEvent(event: UserProfileEvent) {
        return when (event) {
            UserProfileEvent.NavigateToLoginScreen -> {
                navigation.navigateToUnauthorizedGraph(rootNavigationController)
            }
            UserProfileEvent.ShowLogoutConfirmDialog -> {
                showLogoutDialog()
            }
        }
    }

    private fun showLogoutDialog() {
        showInfoDialog(
            negativeText = CoreR.string.no,
            positiveText = CoreR.string.yes,
            callbackRequestCode = RC_DIALOG_LOGOUT,
            title = R.string.user_profile_dialog_logout_title,
            message = R.string.user_profile_dialog_logout_message
        )
    }

}