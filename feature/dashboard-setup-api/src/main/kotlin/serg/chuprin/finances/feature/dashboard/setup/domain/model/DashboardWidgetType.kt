package serg.chuprin.finances.feature.dashboard.setup.domain.model

/**
 * Created by Sergey Chuprin on 28.11.2020.
 */
enum class DashboardWidgetType {
    MONEY_ACCOUNTS,
    BALANCE,
    PERIOD_SELECTOR,
    RECENT_TRANSACTIONS,
    CATEGORIES;

    companion object {

        val default = listOf(
            MONEY_ACCOUNTS,
            BALANCE,
            PERIOD_SELECTOR,
            RECENT_TRANSACTIONS,
            CATEGORIES
        )
    }

}