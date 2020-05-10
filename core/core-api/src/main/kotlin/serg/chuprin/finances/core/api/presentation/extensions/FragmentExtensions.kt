package serg.chuprin.finances.core.api.presentation.extensions

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

/**
 * Created by Sergey Chuprin on 10.05.2020.
 */
fun Fragment.setupToolbar(toolbar: Toolbar, block: (ActionBar.() -> Unit)? = null) {
    (requireActivity() as AppCompatActivity).run {
        setSupportActionBar(toolbar)
        supportActionBar?.run { block?.invoke(this) }
    }
}