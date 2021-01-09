package serg.chuprin.finances.core.api.domain.model

import serg.chuprin.finances.core.api.domain.model.category.Category
import java.math.BigDecimal

/**
 * Created by Sergey Chuprin on 24.12.2020.
 */
typealias CategoryShares = List<Pair<Category?, BigDecimal>>