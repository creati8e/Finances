package serg.chuprin.finances.feature.dashboard.presentation.view

import android.os.Bundle
import android.view.View
import coil.api.load
import kotlinx.android.synthetic.main.fragment_dashboard.*
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromComponent
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.feature.dashboard.R
import serg.chuprin.finances.feature.dashboard.presentation.di.DashboardComponent
import serg.chuprin.finances.core.api.R as CoreR

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
class DashboardFragment : BaseFragment(R.layout.fragment_dashboard) {

    private val viewModel by viewModelFromComponent { DashboardComponent.get() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userPhotoImageView.clipToOutline = true
        with(viewModel) {
            userPhotoLiveData { photoUrl ->
                userPhotoImageView.load(photoUrl) {
                    error(CoreR.drawable.ic_user_photo_placeholder)
                    placeholder(CoreR.drawable.ic_user_photo_placeholder)
                }
            }
        }
    }

}