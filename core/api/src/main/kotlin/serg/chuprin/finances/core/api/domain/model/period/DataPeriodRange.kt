package serg.chuprin.finances.core.api.domain.model.period

/**
 * Created by Sergey Chuprin on 27.12.2020.
 */
class DataPeriodRange(
    override val start: DataPeriod,
    override val endInclusive: DataPeriod,
) : ClosedRange<DataPeriod>, Iterable<DataPeriod> {

    private class DataPeriodIterator(
        start: DataPeriod,
        private val endInclusive: DataPeriod,
    ) : Iterator<DataPeriod> {

        private var initValue = start

        init {
            require(start.periodType == endInclusive.periodType) {
                "Unable to iterate from start data period ($start) to end ($endInclusive) " +
                        "because they has different period types."
            }
        }

        override fun next(): DataPeriod = initValue++

        override fun hasNext(): Boolean = initValue <= endInclusive

    }

    override fun iterator(): Iterator<DataPeriod> {
        return DataPeriodIterator(start = start, endInclusive = endInclusive)
    }

}