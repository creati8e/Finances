package serg.chuprin.finances.core.api.presentation.view.dialog.info

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.showDialog

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
fun Fragment.showInfoDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes positiveText: Int,
    @StringRes negativeText: Int,
    callbackRequestCode: Int
) {
    val arguments = InfoDialogArguments(
        title = getString(title),
        message = getString(message),
        positiveText = getString(positiveText),
        negativeText = getString(negativeText),
        callbackRequestCode = callbackRequestCode
    )
    showDialog<InfoDialogFragment>(childFragmentManager, arguments)
}