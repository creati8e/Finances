package serg.chuprin.finances.core.api.presentation.view.extensions.fragment

import android.app.Application
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */

// region Toasts.

fun Fragment.shortToast(@StringRes textStringRes: Int) {
    Toast.makeText(requireContext(), textStringRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.shortToast(text: String) {
    Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
}

// endregion

fun Fragment.setupToolbar(toolbar: Toolbar, block: (ActionBar.() -> Unit)? = null) {
    (requireActivity() as AppCompatActivity).run {
        setSupportActionBar(toolbar)
        supportActionBar?.run { block?.invoke(this) }
    }
}

fun Fragment.setToolbarTitle(title: String) {
    (requireActivity() as AppCompatActivity).supportActionBar!!.title = title
}

val Fragment.application: Application
    get() = requireActivity().application