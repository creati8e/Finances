package serg.chuprin.finances.core.impl.presentation.model.builder

import serg.chuprin.finances.core.api.domain.model.Id
import serg.chuprin.finances.core.api.domain.model.transaction.PlainTransactionType
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

    private val transactionsReportBaseTransitionName: String
        get() {
            val stringRes = R.string.transition_dashboard_categories_to_transactions_report
            return resourceManger.getString(stringRes)
        }

    override fun buildForMoneyAccountCreation(): String {
        return resourceManger.getString(R.string.transition_money_account)
    }

    override fun buildForTransaction(transactionId: Id?): String {
        val baseString = resourceManger.getString(R.string.transition_transaction)
        if (transactionId == null) {
            return baseString
        }
        return "${baseString}_${transactionId.value}"
    }

    override fun buildForTransactionsReport(categoryId: Id?): String {
        return "${transactionsReportBaseTransitionName}${categoryId?.value}"
    }

    override fun buildForTransactionsReportUnknownCategory(
        transactionType: PlainTransactionType
    ): String {
        return "${transactionsReportBaseTransitionName}_unknown_category_$transactionType"
    }

    override fun buildForTransactionsReportOtherCategory(
        transactionType: PlainTransactionType
    ): String {
        return "${transactionsReportBaseTransitionName}_other_category_$transactionType"
    }

    override fun buildForForMoneyAccountDetails(moneyAccountId: Id): String {
        return "${resourceManger.getString(R.string.transition_money_account_details)}${moneyAccountId.value}"
    }

}