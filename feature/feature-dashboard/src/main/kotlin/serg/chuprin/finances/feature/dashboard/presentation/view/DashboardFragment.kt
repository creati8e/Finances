package serg.chuprin.finances.feature.dashboard.presentation.view

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import coil.api.load
import kotlinx.android.synthetic.main.fragment_dashboard.*
import serg.chuprin.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.di.DashboardComponent
import serg.chuprin.finances.feature.dashboard.presentation.view.adapter.renderer.DashboardHeaderWidgetCellRenderer
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModel by viewModelFromComponent { DashboardComponent.get() }

    private val cellsAdapter = DiffMultiViewAdapter(DiffCallback<BaseCell>()).apply {
        registerRenderer(DashboardHeaderWidgetCellRenderer())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.adapter = cellsAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        with(viewModel) {
            cellsLiveData(cellsAdapter::setItems)
            userPhotoLiveData { photoUrl ->
                userPhotoImageView.load(photoUrl) {
                    error(CoreR.drawable.ic_user_photo_placeholder)
                    placeholder(CoreR.drawable.ic_user_photo_placeholder)
                }
            }
        }
    }

}