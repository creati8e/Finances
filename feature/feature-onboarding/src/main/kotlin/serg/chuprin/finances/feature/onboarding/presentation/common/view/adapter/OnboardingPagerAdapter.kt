package serg.chuprin.finances.feature.onboarding.presentation.common.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import serg.chuprin.finances.feature.onboarding.presentation.accountssetup.view.AccountsSetupOnboardingFragment
import serg.chuprin.finances.feature.onboarding.presentation.currencychoice.view.CurrencyChoiceOnboardingFragment

/**
 * Created by Sergey Chuprin on 07.04.2020.
 */
class OnboardingPagerAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return CurrencyChoiceOnboardingFragment()
        }
        return AccountsSetupOnboardingFragment()
    }
}