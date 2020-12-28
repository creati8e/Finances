package serg.chuprin.finances.feature.categories.impl.presentation.view

import android.os.Bundle
import android.view.View
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge
import kotlinx.android.synthetic.main.fragment_categories_list.*
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.setupToolbar
import serg.chuprin.finances.feature.categories.impl.R

/**
 * Created by Sergey Chuprin on 28.12.2020.
 */
class CategoriesListFragment : BaseFragment(R.layout.fragment_categories_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edgeToEdge {
            view.fit { Edge.Top }
            recyclerView.fit { Edge.Bottom }
        }

        setupToolbar(toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.categories_list_toolbar_title)
        }
    }

}