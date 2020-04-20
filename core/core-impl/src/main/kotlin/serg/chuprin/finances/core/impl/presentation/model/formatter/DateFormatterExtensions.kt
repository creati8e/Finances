package serg.chuprin.finances.core.impl.presentation.model.formatter

import java.time.format.DateTimeFormatter
import java.util.*

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal fun DateTimeFormatter.localized() = withLocale(Locale.getDefault())