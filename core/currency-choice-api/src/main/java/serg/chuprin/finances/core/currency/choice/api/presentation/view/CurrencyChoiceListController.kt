package serg.chuprin.finances.core.currency.choice.api.presentation.view

import android.graphics.Color
import android.view.ViewGroup
import android.widget.TextView
import androidx.transition.TransitionManager
import androidx.transition.doOnEnd
import androidx.transition.doOnStart
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import serg.chuprin.finances.core.currency.choice.api.presentation.view.widget.CurrencyChoiceListView
import serg.chuprin.finances.core.api.presentation.view.extensions.makeGone
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisible

/**
 * Created by Sergey Chuprin on 24.10.2020.
 */
class CurrencyChoiceListController(
    private val animationContainer: ViewGroup,
    private val chosenCurrencyTextView: TextView,
    private val currencyChoiceView: CurrencyChoiceListView
) {

    fun showOrHide(show: Boolean) {
        val transition = MaterialContainerTransform().apply {
            // Adding target fixes performance issue.
            if (show) {
                addTarget(currencyChoiceView)
            } else {
                addTarget(chosenCurrencyTextView)
            }
            startView = if (show) chosenCurrencyTextView else currencyChoiceView
            endView = if (show) currencyChoiceView else chosenCurrencyTextView

            scrimColor = Color.TRANSPARENT
            setPathMotion(MaterialArcMotion())
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
        }
        if (show) {
            transition.doOnStart {
                currencyChoiceView.resetScroll()
                currencyChoiceView.clearSearchQuery()
            }
            transition.doOnEnd {
                currencyChoiceView.showKeyboard()
            }
        }
        TransitionManager.beginDelayedTransition(animationContainer, transition)
        if (show) {
            currencyChoiceView.makeVisible()
        } else {
            currencyChoiceView.makeGone()
        }
    }

}