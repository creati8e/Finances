package serg.chuprin.finances.core.api.presentation.view.widget

import android.content.Context
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

/**
 * Created by Sergey Chuprin on 12.04.2020.
 *
 * Edit text with predefined number input type and which disallows text selection.
 */
class AmountEditText : TextInputEditText {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inputType = InputType.TYPE_CLASS_NUMBER
        keyListener = DigitsKeyListener.getInstance("0123456789.,")
    }

    /**
     * Disallow text selection.
     */
    override fun onSelectionChanged(start: Int, end: Int) {
        val text: CharSequence? = text
        if (text != null) {
            if (start != text.length || end != text.length) {
                setSelection(text.length, text.length)
                return
            }
        }
        super.onSelectionChanged(start, end)
    }


}