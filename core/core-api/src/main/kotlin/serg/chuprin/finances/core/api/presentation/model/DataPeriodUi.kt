package serg.chuprin.finances.core.api.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import java.time.LocalDateTime

/**
 * Created by Sergey Chuprin on 11.05.2020.
 */
@Parcelize
class DataPeriodUi(
    val endDate: LocalDateTime,
    val startDate: LocalDateTime,
    val periodType: DataPeriodType
) : Parcelable