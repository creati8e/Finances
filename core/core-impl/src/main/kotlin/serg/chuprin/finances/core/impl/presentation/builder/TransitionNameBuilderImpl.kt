package serg.chuprin.finances.core.impl.presentation.builder

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.presentation.builder.TransitionNameBuilder
import serg.chuprin.finances.core.api.presentation.model.manager.ResourceManger
import serg.chuprin.finances.core.impl.R
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 08.05.2020.
 */
internal class TransitionNameBuilderImpl @Inject constructor(
    private val resourceManger: ResourceManger
) : TransitionNameBuilder {

    override fun buildForTransactionsReport(categoryId: Id?): String {
        return "${resourceManger.getString(R.string.transition_dashboard_categories_to_transactions_report)}${categoryId?.value}"
    }

    override fun buildForForMoneyAccountDetails(moneyAccountId: Id): String {
        return "${resourceManger.getString(R.string.transition_money_account)}${moneyAccountId.value}"
    }

}