package serg.chuprin.finances.core.impl.data.datasource.assets

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Created by Sergey Chuprin on 19.04.2020.
 */
@Serializable
internal class CategoryAssetDto(
    @SerialName("name")
    val name: String,

    @SerialName("color")
    val colorHex: String,

    @SerialName("id")
    var id: String,

    @SerialName("is_income")
    val isIncome: Boolean,

    @SerialName("parent_category_id")
    var parentCategoryId: String? = null
)