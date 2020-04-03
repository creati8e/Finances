package serg.chuprin.finances.core.api.presentation.view.extensions

import android.widget.Toast
import androidx.annotation.StringRes
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