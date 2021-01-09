package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.category.Category
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 24.12.2020.
 */
class CategoryShares(
    private val source: List<Pair<Category?, BigDecimal>>
) : List<Pair<Category?, BigDecimal>> by source {

    override fun equals(other: Any?): Boolean {
        return (other as? CategoryShares)?.source == source
    }

    override fun hashCode(): Int = source.hashCode()

}