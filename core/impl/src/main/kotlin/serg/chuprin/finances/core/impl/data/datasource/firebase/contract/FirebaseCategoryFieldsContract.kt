package serg.chuprin.finances.core.impl.data.datasource.firebase.contract

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
internal object FirebaseCategoryFieldsContract {

    const val COLLECTION_NAME = "category"

    const val FIELD_TYPE = "type"
    const val FIELD_NAME = "name"
    const val FIELD_OWNER_ID = "owner_id"
    const val FIELD_PARENT_ID = "parent_id"
    const val FIELD_COLOR_HEX = "color_hex"

    const val TYPE_INCOME = "income"
    const val TYPE_EXPENSE = "expense"

}