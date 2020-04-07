package serg.chuprin.finances.feature.onboarding.presentation.view

import android.graphics.Color
import android.os.Bundle
import android.transition.TransitionManager
import android.view.View
import androidx.activity.addCallback
import androidx.core.transition.doOnEnd
import androidx.core.view.isVisible
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import kotlinx.android.synthetic.main.fragment_onboarding_currency_choice.*
import kotlinx.android.synthetic.main.view_currency_choice.view.*
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.viewModelFromProvider
import serg.chuprin.finances.core.api.presentation.view.BaseFragment
import serg.chuprin.finances.core.api.presentation.view.extensions.makeGone
import serg.chuprin.finances.core.api.presentation.view.extensions.makeVisible
import serg.chuprin.finances.core.api.presentation.view.extensions.onClick
import serg.chuprin.finances.core.api.presentation.view.extensions.showKeyboard
import serg.chuprin.finances.feature.onboarding.R
import serg.chuprin.finances.feature.onboarding.presentation.di.OnboardingComponent
import serg.chuprin.finances.feature.onboarding.presentation.model.cells.CurrencyCell
import java.util.*

/**
 * Created by Sergey Chuprin on 05.04.2020.
 */
class CurrencyChoiceOnboardingFragment :
    BaseFragment(R.layout.fragment_onboarding_currency_choice) {

    private val viewModel by viewModelFromProvider { parentComponent.currencyChoiceViewModel }

    private val parentComponent: OnboardingComponent
        get() = (parentFragment as OnboardingContainerFragment).component

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (currencyChoiceView.isVisible) {
                showOrHideCurrencyChoice(show = false)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currencyChoiceView.onCurrencyCellChosen = { cell ->
            textInputLayout.text = cell.displayName
            showOrHideCurrencyChoice(show = false)
        }
        currencyChoiceView.onCloseClicked = {
            showOrHideCurrencyChoice(show = false)
        }
        textInputLayout.onClick {
            currencyChoiceView.setCells(buildCells())
            showOrHideCurrencyChoice(show = true)
        }
    }

    private fun showOrHideCurrencyChoice(show: Boolean) {
        val transition = MaterialContainerTransform(requireContext()).apply {
            // Manually tell the container transform which Views to transform between.
            startView = if (show) textInputLayout else currencyChoiceView
            endView = if (show) currencyChoiceView else textInputLayout

            // Optionally add a curved path to the transform
            pathMotion = MaterialArcMotion()
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH

            // Since View to View transforms often are not transforming into full screens,
            // remove the transition's scrim.
            scrimColor = Color.TRANSPARENT
        }

        if (show) {
            transition.doOnEnd {
                currencyChoiceView.searchEditText.showKeyboard()
            }
        }

        // Begin the transition by changing properties on the start and end views or
        // removing/adding them from the hierarchy.
        TransitionManager.beginDelayedTransition(constraintLayout, transition)
        if (show) {
            currencyChoiceView.makeVisible()
        } else {
            currencyChoiceView.makeGone()
        }
    }

    private fun buildCells(): List<CurrencyCell> {
        val locale = Locale.getDefault()
        return Currency
            .getAvailableCurrencies()
            .sortedBy(Currency::getCurrencyCode)
            .map { currency ->
                val displayName = currency.getDisplayName(locale).capitalize(locale)
                CurrencyCell(
                    currencyCode = currency.currencyCode,
                    displayName = "${currency.currencyCode} - $displayName"
                )
            }
    }

}