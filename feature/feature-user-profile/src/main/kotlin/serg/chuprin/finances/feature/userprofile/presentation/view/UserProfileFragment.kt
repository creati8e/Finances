package serg.chuprin.finances.feature.userprofile.presentation.view

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_profile.*
import serg.chuprin.finances.core.api.presentation.model.cells.BaseCell
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.adapter.DiffMultiViewAdapter
import serg.chuprin.finances.core.api.presentation.view.adapter.diff.DiffCallback
import serg.chuprin.finances.core.api.presentation.view.setEnterSharedElementTransition
import serg.chuprin.finances.feature.userprofile.R
import serg.chuprin.finances.feature.userprofile.presentation.model.cells.UserProfileImageCell
import serg.chuprin.finances.feature.userprofile.presentation.view.adapter.renderer.UserProfileImageCellRenderer

/**
 * Created by Sergey Chuprin on 25.07.2020.
 */
class UserProfileFragment : BaseFragment(R.layout.fragment_user_profile) {

    private val cellsAdapter = DiffMultiViewAdapter(DiffCallback<BaseCell>()).apply {
        registerRenderer(UserProfileImageCellRenderer())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEnterSharedElementTransition()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        with(recyclerView) {
            adapter = cellsAdapter.apply {
                setItems(
                    listOf(
                        UserProfileImageCell(
                            "https://images.unsplash.com/profile-1557453908829-d9705eb74435?dpr=1&auto=format&fit=crop&w=150&h=150&q=60&crop=faces&bg=fff",
                            "Sergey Chuprin",
                            "gregamer@gmail.com"
                        )
                    )
                )
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

}