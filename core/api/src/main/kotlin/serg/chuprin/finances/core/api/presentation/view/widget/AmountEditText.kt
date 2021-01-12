package serg.chuprin.finances.core.api.presentation.view.widget

import android.content.Context
import android.text.InputType
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputEditText
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.core.api.presentation.view.extensions.drawableEnd
import serg.chuprin.finances.core.api.presentation.view.extensions.getPrimaryTextColor
import serg.chuprin.finances.core.api.presentation.view.extensions.shouldIgnoreChanges
import java.math.BigDecimal

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

    private var textWatcher: TextWatcher? = null
    private var formatter: ((String) -> String)? = null

    init {
        inputType = InputType.TYPE_CLASS_NUMBER
        keyListener = DigitsKeyListener.getInstance("0123456789.,")
        compoundDrawablePadding = context.dpToPx(8)

        if (isInEditMode) {
            setCurrencySymbol("$")
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        textWatcher = doAfterTextChanged { editable ->
            if (!shouldIgnoreChanges) {
                format(editable?.toString().orEmpty())
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeTextChangedListener(textWatcher)
        textWatcher = null
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

    fun setCurrencySymbol(symbol: String) {
        drawableEnd = TextDrawable(
            text = symbol,
            textColor = context.getPrimaryTextColor(),
            textSize = textSize - (textSize / 4)
        )
    }

    fun setFormatter(formatter: (String) -> String) {
        this.formatter = formatter
    }

    fun setAmount(enteredAmount: BigDecimal?) {
        format(enteredAmount?.toString().orEmpty())
    }

    private fun format(input: String) {
        val formatter = this.formatter ?: return

        shouldIgnoreChanges = true
        setText(formatter(input))
        shouldIgnoreChanges = false
    }

}