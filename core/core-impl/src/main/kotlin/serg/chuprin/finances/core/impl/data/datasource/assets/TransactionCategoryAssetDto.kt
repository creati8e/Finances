package serg.chuprin.finances.core.impl.data.datasource.assets

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
@Serializable
class TransactionCategoryAssetDto(
    @SerialName("name")
    val name: String,
    @SerialName("id")
    val id: String,
    @SerialName("parent_category_id")
    val parentCategoryId: String?
)