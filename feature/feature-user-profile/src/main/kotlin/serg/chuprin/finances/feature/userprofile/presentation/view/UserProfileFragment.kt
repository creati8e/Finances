package serg.chuprin.finances.feature.userprofile.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_profile.*
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.setEnterSharedElementTransition
import serg.chuprin.finances.feature.userprofile.R
import serg.chuprin.finances.feature.userprofile.presentation.di.UserProfileComponent
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer.UserProfileDataPeriodTypeCellRenderer
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer.UserProfileImageCellRenderer

/**
 * Created by Sergey Chuprin on 25.07.2020.
 */
class UserProfileFragment : BaseFragment(R.layout.fragment_user_profile) {

    private val viewModel by viewModelFromComponent { UserProfileComponent.get() }

    private val cellsAdapter = DiffMultiViewAdapter(DiffCallback<BaseCell>()).apply {
        registerRenderer(UserProfileImageCellRenderer())
        registerRenderer(UserProfileDataPeriodTypeCellRenderer())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEnterSharedElementTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(recyclerView) {
            adapter = cellsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        with(viewModel) {
            cellsLiveData(cellsAdapter::setItems)
        }
    }

}