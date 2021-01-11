package serg.chuprin.finances.core.api.presentation.view.extensions

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.getSystemService
import kotlin.math.max
import kotlin.math.roundToInt

/**
 * Created by Sergey Chuprin on 21.03.2019.
 */

val View.alphaInt: Int
    get() = (alpha * 255.0f).roundToInt()

// region Resource

fun View.getString(@StringRes stringResId: Int): String = context.getString(stringResId)

inline fun View.onClick(crossinline action: () -> Unit) = setOnClickListener { action.invoke() }

inline fun <V : View> V.onViewClick(crossinline action: (V) -> Unit) {
    setOnClickListener {
        @Suppress("UNCHECKED_CAST")
        action.invoke(it as V)
    }
}

inline fun <reified T : View> T.onViewLongClick(crossinline action: (T) -> Unit) {
    setOnLongClickListener {
        action(it as T)
        true
    }
}

inline fun View.onLongClick(crossinline action: () -> Unit) {
    setOnLongClickListener {
        action()
        true
    }
}

// endregion

fun EditText.getTextString(): String = text?.toString().orEmpty()

// region Visibility

fun View.makeGone() {
    if (visibility != View.GONE) {
        visibility = View.GONE
    }
}

fun View.makeVisible() {
    if (visibility != View.VISIBLE) {
        visibility = View.VISIBLE
    }
}

fun View.makeVisibleOrGone(visible: Boolean) {
    if (visible) {
        makeVisible()
    } else {
        makeGone()
    }
}

// endregion

var TextView.drawableStart: Drawable?
    get() = drawables[0]
    set(value) = updateDrawable(start = value)

var TextView.drawableEnd: Drawable?
    get() = drawables[2]
    set(value) = updateDrawable(end = value)

val TextView.drawables: Array<Drawable?>
    get() = compoundDrawablesRelative

fun TextView.updateDrawable(
    start: Drawable? = null,
    top: Drawable? = null,
    end: Drawable? = null,
    bottom: Drawable? = null
) {
    setCompoundDrawablesRelativeWithIntrinsicBounds(start, top, end, bottom)
}

fun TextView.setTextOrHide(text: String) {
    makeVisibleOrGone(text.isNotEmpty())
    if (text.isNotEmpty()) {
        setText(text)
    }
}

const val VIEW_TAG_IGNORE_CHANGES = "VIEW_TAG_IGNORE_CHANGES"

inline var View.shouldIgnoreChanges: Boolean
    get() = tag == VIEW_TAG_IGNORE_CHANGES
    set(value) {
        tag = VIEW_TAG_IGNORE_CHANGES.takeIf { value }
    }

inline fun <V : View> V.doIgnoringChanges(block: V.() -> Unit) {
    shouldIgnoreChanges = true
    block()
    shouldIgnoreChanges = false
}

fun View.adjustHeightToFillParent() {
    val parentViewGroup = parent as? ViewGroup ?: return

    val parentPadding = parentViewGroup.paddingTop + parentViewGroup.paddingBottom
    val height = parentViewGroup.height - top - parentPadding
    val adjustedHeight = max(minimumHeight, height)

    layoutParams.height = adjustedHeight

    requestLayout()
}

inline fun EditText.doOnEditorAction(
    action: Int = EditorInfo.IME_ACTION_DONE,
    crossinline func: () -> Unit
) {
    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == action) {
            func()
            true
        } else {
            false
        }
    }
}

fun View.showKeyboard() {
    val context = this.context ?: return
    requestFocus()
    context.getSystemService<InputMethodManager>()!!.showSoftInput(this, 0)
}

fun View.hideKeyboard() {
    context.getSystemService<InputMethodManager>()!!.hideSoftInputFromWindow(windowToken, 0)
}
