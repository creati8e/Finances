package serg.chuprin.finances.core.api.domain.model.period

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure


/**
 * Created by Sergey Chuprin on 27.12.2020.
 */
object DataPeriodTest : Spek({

    Feature("Data period") {

        Scenario("Data period range test") {

            val endDataPeriod = DataPeriod.from(DataPeriodType.MONTH)
            val startDataPeriod = endDataPeriod.minusPeriods(5)

            val endToStartRange = (endDataPeriod..startDataPeriod)
            expectThat(endToStartRange.toList().size).isEqualTo(0)

            val startToEndRange = (startDataPeriod..endDataPeriod)
            expectThat(startToEndRange.toList().size).isEqualTo(6)
        }

        Scenario("Data periods comparison") {

            When("Compare data periods with different period types") {}

            Then("Comparison throws an exception") {
                expectCatching {
                    val datePeriod = DataPeriod.from(DataPeriodType.WEEK)
                    val otherDatPeriod = DataPeriod.from(DataPeriodType.MONTH)
                    datePeriod.compareTo(otherDatPeriod)
                }.isFailure()
            }

            When("Compare data periods with same period types") {}

            Then("Data period with earlier start date is smaller") {
                val dataPeriod = DataPeriod.from(DataPeriodType.MONTH).minusPeriods(1)
                val otherDataPeriod = DataPeriod.from(DataPeriodType.MONTH)

                expectThat(dataPeriod.compareTo(otherDataPeriod)).isEqualTo(-1)
            }

            When("Compare data periods with same period types and same start data") {}

            Then("They are equals") {
                val dataPeriod = DataPeriod.from(DataPeriodType.MONTH)
                val otherDataPeriod = DataPeriod.from(DataPeriodType.MONTH)

                expectThat(dataPeriod.compareTo(otherDataPeriod)).isEqualTo(0)
            }

        }

    }

})