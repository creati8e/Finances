package serg.chuprin.finances.core.api.presentation.view.popup.menu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.cell_overflow_menu.view.*
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.model.cells.PopupMenuCell
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisibleOrGone

/**
 * Created by Sergey Chuprin on 17.03.2020.
 */
class PopupMenuWindowListAdapter(
    context: Context,
    menuCells: List<PopupMenuCell>
) : ArrayAdapter<PopupMenuCell>(
    context,
    LAYOUT_RES,
    menuCells
) {

    private companion object {
        private val LAYOUT_RES = R.layout.cell_overflow_menu
    }

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(LAYOUT_RES, parent, false)
        val cell = getItem(position) ?: return view
        return view.apply {
            textView.text = cell.title
            with(checkBox) {
                makeVisibleOrGone(cell.isCheckable)
                if (cell.isCheckable) {
                    isChecked = cell.isChecked
                }
            }
        }
    }

}