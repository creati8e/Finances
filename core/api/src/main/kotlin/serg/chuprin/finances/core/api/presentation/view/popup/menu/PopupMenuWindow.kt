package serg.chuprin.finances.core.api.presentation.view.popup.menu

import android.content.Context
import android.widget.ArrayAdapter
import serg.chuprin.finances.core.api.presentation.model.cells.PopupMenuCell
import serg.chuprin.finances.core.api.presentation.view.popup.CustomPopupWindow
import serg.chuprin.finances.core.api.presentation.view.popup.menu.adapter.PopupMenuWindowListAdapter

/**
 * Created by Sergey Chuprin on 17.03.2020.
 */
class PopupMenuWindow<T : PopupMenuCell>(
    cells: List<T>,
    callback: ((cell: T) -> Unit)?,
    positionCallback: ((position: Int) -> Unit)? = null
) : CustomPopupWindow<T>(cells, callback, positionCallback) {

    override fun createAdapter(context: Context, cells: List<T>): ArrayAdapter<*> {
        return PopupMenuWindowListAdapter(context, cells)
    }

}