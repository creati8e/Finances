package serg.chuprin.finances.core.api.extensions

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import timber.log.Timber
import java.time.LocalDateTime
import java.util.*

/**
 * Created by Sergey Chuprin on 18.04.2020.
 */
object DateTimeExtensionsTest : Spek({

    Feature("Date time extensions") {

        Timber.plant(TimberConsoleTree())

        Scenario("Converting date to local date time") {

            val date = Date()
            val calendar = Calendar.getInstance().apply {
                time = date
                timeZone = TimeZone.getTimeZone("UTC")
            }

            lateinit var dateTime: LocalDateTime

            When("Method is called") {
                dateTime = date.toLocalDateTimeUTC()
            }

            Then("Converted correctly") {
                expectThat(calendar.get(Calendar.YEAR)).isEqualTo(dateTime.year)
                expectThat(calendar.get(Calendar.MONTH) + 1).isEqualTo(dateTime.month.value)
                expectThat(calendar.get(Calendar.DAY_OF_MONTH)).isEqualTo(dateTime.dayOfMonth)
                expectThat(calendar.get(Calendar.HOUR_OF_DAY)).isEqualTo(dateTime.hour)
                expectThat(calendar.get(Calendar.MINUTE)).isEqualTo(dateTime.minute)
                expectThat(calendar.get(Calendar.SECOND)).isEqualTo(dateTime.second)
            }

            lateinit var convertedDate: Date

            When("Convert back") {
                convertedDate = dateTime.toDateUTC()
            }

            Then("Converted date equals to original") {
                val convertedCalendar = Calendar.getInstance()
                    .apply {
                        time = convertedDate
                        timeZone = TimeZone.getTimeZone("UTC")
                    }
                expectThat(convertedCalendar.get(Calendar.YEAR))
                    .isEqualTo(calendar.get(Calendar.YEAR))

                expectThat(convertedCalendar.get(Calendar.MONTH) + 1)
                    .isEqualTo(calendar.get(Calendar.MONTH) + 1)

                expectThat(convertedCalendar.get(Calendar.DAY_OF_MONTH))
                    .isEqualTo(calendar.get(Calendar.DAY_OF_MONTH))

                expectThat(convertedCalendar.get(Calendar.HOUR_OF_DAY))
                    .isEqualTo(calendar.get(Calendar.HOUR_OF_DAY))

                expectThat(convertedCalendar.get(Calendar.MINUTE))
                    .isEqualTo(calendar.get(Calendar.MINUTE))

                expectThat(convertedCalendar.get(Calendar.SECOND))
                    .isEqualTo(calendar.get(Calendar.SECOND))
            }

        }

    }

})