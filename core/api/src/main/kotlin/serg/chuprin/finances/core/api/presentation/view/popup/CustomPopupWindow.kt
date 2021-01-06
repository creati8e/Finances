package serg.chuprin.finances.core.api.presentation.view.popup

import android.content.Context
import android.graphics.Point
import android.view.*
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.*
import androidx.core.content.getSystemService
import com.github.ajalt.timberkt.Timber
import serg.chuprin.finances.core.api.R
import serg.chuprin.finances.core.api.presentation.view.extensions.dpToPx
import serg.chuprin.finances.core.api.presentation.view.extensions.drawable
import serg.chuprin.finances.core.api.presentation.view.extensions.getDimenDpFloat


/**
 * Created by Sergey Chuprin on 22.12.2019.
 */
abstract class CustomPopupWindow<T>(
    protected val cells: List<T>,
    protected val callback: ((cell: T) -> Unit)?,
    protected val positionCallback: ((position: Int) -> Unit)? = null
) {

    private var isDisplayed = false

    fun show(anchor: View, showAtAnchorCenter: Boolean = false) {
        require(!isDisplayed) {
            "Popup window is already displayed"
        }

        with(createWindow(anchor)) {
            val (xOffset, yOffset) = calculateOffsets(anchor, showAtAnchorCenter)
            showAsDropDown(anchor, xOffset, yOffset, Gravity.START)
            setupBackgroundClipping()
        }
        isDisplayed = true
    }

    protected abstract fun createAdapter(context: Context, cells: List<T>): ArrayAdapter<*>

    private fun PopupWindow.calculateOffsets(
        anchor: View,
        showAtAnchorCenter: Boolean
    ): Pair<Int, Int> {
        val (xOffset, yOffset) = if (showAtAnchorCenter) {
            val anchorCenter = anchor.width - (anchor.width / 4)
            anchorCenter - width to -anchor.height
        } else {
            anchor.width - width to -anchor.height
        }
        return adjustWidthAndHorizontalOffset(anchor, xOffset) to yOffset
    }

    /**
     * This method helps to fit popup window on screen with horizontal margin.
     */
    private fun PopupWindow.adjustWidthAndHorizontalOffset(
        anchor: View,
        offset: Int
    ): Int {
        var adjustedOffset = offset
        val margin = anchor.context.dpToPx(16)
        if ((getScreenWidth(anchor.context) - width) < margin * 2) {
            adjustedOffset += margin
            width -= margin
        }
        return adjustedOffset
    }

    /**
     * This is required to clip ripples of items in ListView.
     */
    private fun PopupWindow.setupBackgroundClipping() {
        try {
            val field = this::class.java.getDeclaredField("mBackgroundView").apply {
                isAccessible = true
            }
            (field.get(this) as ViewGroup).apply {
                clipChildren = true
                clipToOutline = true
                outlineProvider = ViewOutlineProvider.BACKGROUND
            }
        } catch (throwable: Throwable) {
            Timber.d { "Unable to clip background of PopupWindow via reflection" }
        }
    }

    private fun createWindow(anchor: View): PopupWindow {
        val context = anchor.context
        return PopupWindow(context).apply {
            val listView = this.createListView(context)

            contentView = listView
            height = WindowManager.LayoutParams.WRAP_CONTENT
            width = measureContentWidth(context, listView.adapter)

            isFocusable = true
            animationStyle = R.style.PopupWindow
            elevation = context.getDimenDpFloat(R.dimen.elevationCardView)
            setBackgroundDrawable(context.drawable(R.drawable.bg_popup_window))
        }
    }

    private fun PopupWindow.createListView(
        context: Context
    ): ListView {
        return ListView(context).apply {
            val padding = context.dpToPx(8)
            setPadding(0, padding, 0, padding)
            setOnItemClickListener { _, _, position, _ ->
                callback?.invoke(cells[position])
                positionCallback?.invoke(position)
                dismiss()
            }

            divider = null
            itemsCanFocus = false
            clipToPadding = false
            isVerticalScrollBarEnabled = false
            adapter = createAdapter(context, cells)
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutAnimation = LayoutAnimationController(
                AnimationUtils.loadAnimation(
                    context,
                    R.anim.popup_window_content
                ), 0.5f
            )
        }
    }

    private fun measureContentWidth(context: Context, adapter: ListAdapter): Int {
        var measureParent: ViewGroup? = null
        var maxWidth = 0
        var cellView: View? = null
        var cellType = 0

        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)

        for (i in 0 until adapter.count) {
            val positionType = adapter.getItemViewType(i)
            if (positionType != cellType) {
                cellType = positionType
                cellView = null
            }

            if (measureParent == null) {
                measureParent = FrameLayout(context)
            }

            cellView = adapter.getView(i, cellView, measureParent)
            cellView!!.measure(widthMeasureSpec, heightMeasureSpec)

            val cellWidth = cellView.measuredWidth

            if (cellWidth > maxWidth) {
                maxWidth = cellWidth
            }
        }

        return maxWidth.coerceAtMost(getScreenWidth(context) - context.dpToPx(16))
    }

    private fun getScreenWidth(context: Context): Int {
        return context.getSystemService<WindowManager>()!!.defaultDisplay.run {
            val size = Point()
            getSize(size)
            size.x
        }
    }
}