package serg.chuprin.finances.core.api.presentation.view

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import java.util.*

/**
 * Created by Sergey Chuprin on 11.04.2020.
 */
class AmountTextWatcher(
    private val currency: Currency,
    private val editText: TextView,
    private val amountFormatter: AmountFormatter
) : TextWatcher {

    private var selfChange: Boolean = false

    init {
        editText.addTextChangedListener(this)
    }

    override fun afterTextChanged(s: Editable?) {
        if (selfChange) {
            return
        }
        selfChange = true
        editText.text = amountFormatter.format(s.toString(), currency)
        selfChange = false
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit


}