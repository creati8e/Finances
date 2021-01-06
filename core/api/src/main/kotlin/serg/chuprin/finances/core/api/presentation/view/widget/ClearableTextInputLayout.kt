package serg.chuprin.finances.core.api.presentation.view.widget

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import reactivecircus.flowbinding.android.view.focusChanges
import reactivecircus.flowbinding.android.widget.textChanges

/**
 * Created by Sergey Chuprin on 18.05.2019.
 */
class ClearableTextInputLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextInputLayout(context, attrs, defStyleAttr) {

    private var scope: CoroutineScope? = null

    init {
        endIconMode = END_ICON_CLEAR_TEXT
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        subscribeToFocusChanges()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        scope?.cancel()
    }

    private fun subscribeToFocusChanges() {
        scope?.cancel()

        val editText = this.editText ?: return
        isEndIconVisible = false

        scope = CoroutineScope(Dispatchers.Main)
        scope?.launch {
            combine(
                editText.textChanges(),
                editText.focusChanges()
            ) { textChange, hasFocus ->
                textChange.isNotEmpty() && hasFocus
            }.onEach { isEndIconVisible ->
                this@ClearableTextInputLayout.isEndIconVisible = isEndIconVisible
            }.launchIn(this)
        }

    }

}