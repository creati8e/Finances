package serg.chuprin.finances.feature.dashboard.setup.presentation.domain.model

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
enum class DashboardWidgetType {
    MONEY_ACCOUNTS,
    BALANCE,
    RECENT_TRANSACTIONS,
    CATEGORIES;

    companion object {

        val default = listOf(
            MONEY_ACCOUNTS,
            BALANCE,
            RECENT_TRANSACTIONS,
            CATEGORIES
        )
    }

}