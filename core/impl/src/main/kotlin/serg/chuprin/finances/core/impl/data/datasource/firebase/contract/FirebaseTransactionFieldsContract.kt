package serg.chuprin.finances.core.impl.data.datasource.firebase.contract

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
internal object FirebaseTransactionFieldsContract {

    const val COLLECTION_NAME = "transaction"

    const val FIELD_TYPE = "type"
    const val FIELD_DATE = "date"
    const val FIELD_AMOUNT = "amount"
    const val FIELD_OWNER_ID = "owner_id"
    const val FIELD_CATEGORY_ID = "category_id"
    const val FIELD_CURRENCY_CODE = "currency_code"
    const val FIELD_MONEY_ACCOUNT_ID = "money_account_id"

    object Type {
        const val PLAIN = "plain"
        const val BALANCE = "balance"
    }

}