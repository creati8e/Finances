package serg.chuprin.finances.core.api.presentation.view

import android.view.Menu
import android.view.MenuItem
import androidx.annotation.MenuRes
import androidx.appcompat.widget.Toolbar

/**
 * Created by Sergey Chuprin on 24.06.2019.
 */
class MenuConfig {

    @MenuRes
    var menuRes: MutableList<Int> = mutableListOf()
    var onPrepareMenu: MutableList<((Menu) -> Unit)> = mutableListOf()
    var onMenuItemClick: MutableList<(MenuItem) -> Unit> = mutableListOf()

    var toolbarProvider: (() -> Toolbar)? = null

    fun withToolbar(toolbarProvider: () -> Toolbar) {
        this.toolbarProvider = toolbarProvider
    }

    fun addMenu(@MenuRes menuRes: Int) {
        this.menuRes.add(menuRes)
    }

    fun addPrepareMenuListener(listener: ((Menu) -> Unit)) {
        this.onPrepareMenu.add(listener)
    }

    fun addMenuItemListener(listener: (MenuItem) -> Unit) {
        this.onMenuItemClick.add(listener)
    }

}

inline fun menuConfig(block: MenuConfig.() -> Unit): MenuConfig = MenuConfig().apply(block)