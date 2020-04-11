package serg.chuprin.finances.core.api.presentation.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.view.extensions.getColorInt
import serg.chuprin.finances.core.api.presentation.view.extensions.getPrimaryTextColor
import java.util.*

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
class AmountTextWatcher(
    private val currency: Currency,
    private val editText: EditText,
    private val amountFormatter: AmountFormatter,
    private val onAmountEntered: (valid: Boolean) -> Unit
) : TextWatcher {

    private var selfChange: Boolean = false

    private val context = editText.context

    override fun afterTextChanged(s: Editable?) {
        if (selfChange) {
            return
        }
        selfChange = true
        val (formattedText, isValid) = amountFormatter.format(s.toString(), currency)
        editText.setText(formattedText)
        onAmountEntered(isValid)
        setEditTextColor(isValid)
        setEndSelection()
        selfChange = false
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

    private fun setEditTextColor(isValidAmount: Boolean) {
        if (isValidAmount) {
            editText.setTextColor(context.getPrimaryTextColor())
        } else {
            editText.setTextColor(context.getColorInt(R.color.colorError))
        }
    }

    private fun setEndSelection() {
        val length = editText.text?.length ?: 0
        editText.setSelection(length, length)
    }

}