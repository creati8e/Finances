package serg.chuprin.finances.core.api.presentation.view.dialog.info

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.core.view.updatePadding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.dialog_fragment_info.view.*
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.view.extensions.*
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.arguments
import serg.chuprin.finances.core.api.presentation.view.extensions.fragment.inflateCustomView

/**
 * Created by Sergey Chuprin on 21.07.2020.
 */
class InfoDialogFragment : AppCompatDialogFragment() {

    interface Callback {

        fun onInfoDialogDismiss(requestCode: Int) = Unit

        fun onInfoDialogPositiveButtonClick(requestCode: Int) = Unit

        fun onInfoDialogNegativeButtonClick(requestCode: Int) = Unit

    }

    private val dialogArgs by arguments<InfoDialogArguments>()

    private val dialogView: View by lazy {
        inflateCustomView(R.layout.dialog_fragment_info)
    }

    private lateinit var callback: Callback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = parentFragment as Callback
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .apply {
                setView(dialogView)
                setupView()
                setCancelable(dialogArgs.isCancellableOnBackPress)
            }
            .create()
            .apply {
                setCanceledOnTouchOutside(dialogArgs.isCanceledOnTouchOutside)
            }
    }

    private fun setupView() {
        with(dialogView) {
            titleTextView.apply {
                text = dialogArgs.title
                makeVisibleOrGone(dialogArgs.title.isNullOrEmpty().not())
            }
            bodyTextView.text = dialogArgs.message
            setupButton(positiveButton, dialogArgs.positiveText) {
                callback.onInfoDialogPositiveButtonClick(dialogArgs.callbackRequestCode)
            }
            setupButton(negativeButton, dialogArgs.negativeText) {
                callback.onInfoDialogNegativeButtonClick(dialogArgs.callbackRequestCode)
            }
            dialogBodyLayout.updatePadding(
                bottom = if (negativeButton.isVisible || positiveButton.isVisible) {
                    requireContext().dpToPx(8)
                } else {
                    requireContext().dpToPx(24)
                }
            )
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        callback.onInfoDialogDismiss(dialogArgs.callbackRequestCode)
        super.onDismiss(dialog)
    }

    override fun onStart() {
        super.onStart()
        isCancelable = dialogArgs.isCancellableOnBackPress
    }

    private inline fun setupButton(
        button: AppCompatButton,
        buttonText: String?,
        crossinline onClick: () -> Unit
    ) {
        if (buttonText.isNullOrBlank()) {
            button.makeGone()
            return
        }
        with(button) {
            text = buttonText
            makeVisible()
            onClick {
                onClick()
                dismiss()
            }
        }
    }

}