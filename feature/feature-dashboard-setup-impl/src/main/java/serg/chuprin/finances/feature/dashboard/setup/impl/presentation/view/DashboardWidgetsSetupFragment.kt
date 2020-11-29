package serg.chuprin.finances.feature.dashboard.setup.impl.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import kotlinx.android.synthetic.main.fragment_dashboard_widgets_setup.*
import serg.chuprin.finances.core.api.presentation.extensions.setupToolbar
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.setEnterSharedElementTransition
import serg.chuprin.finances.feature.dashboard.setup.impl.R

/**
 * Created by Sergey Chuprin on 29.11.2020.
 */
class DashboardWidgetsSetupFragment : BaseFragment(R.layout.fragment_dashboard_widgets_setup) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEnterSharedElementTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
        }
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

}