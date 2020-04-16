package serg.chuprin.finances.core.mvi.store

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import serg.chuprin.finances.core.mvi.store.counter.CounterStoreBootstrapper
import serg.chuprin.finances.core.mvi.store.counter.CounterTestIntent
import serg.chuprin.finances.core.mvi.store.counter.CounterTestState
import serg.chuprin.finances.core.mvi.store.counter.CounterTestStoreFactory
import kotlin.test.assertEquals
import kotlin.test.assertFalse

/**
 * Created by Sergey Chuprin on 17.03.2019.
 */
@FlowPreview
object BaseStateStoreTest : Spek({

    Feature("Store test") {

        Scenario("Simple store without async operations") {

            val testStore = CounterTestStoreFactory.build()

            When("Dispatch \"Increment action\"") {
                testStore.testSubscribe()
                testStore.dispatch(CounterTestIntent.Increment)
            }

            Then("New state with updated counter is emitted") {
                assertEquals(2, testStore.capturedStates.size)
                assertEquals(CounterTestState(counter = 0), testStore.capturedStates.first)
                assertEquals(CounterTestState(counter = 1), testStore.state)
            }
        }


        Scenario("Bootstrapping store") {

            val bootstrappedValue = 100
            val bootstrapper = CounterStoreBootstrapper(bootstrappedValue)
            val testStore = CounterTestStoreFactory.build(bootstrapper = bootstrapper)

            When("Store created and subscriber subscribed") {
                testStore.testSubscribe()
            }

            Then("Bootstrapper emitted action and state changed") {
                assertEquals(2, testStore.capturedStates.size)
                assertEquals(bootstrappedValue, testStore.state.counter)
            }

        }


        Scenario("Bootstrapper emits further updates") {

            val bootstrappedValue = 100
            val bootstrapper = CounterStoreBootstrapper(bootstrappedValue)
            val testStore = CounterTestStoreFactory.build(bootstrapper = bootstrapper)

            When("Store created and subscriber subscribed") {
                testStore.testSubscribe()
            }

            Then("Bootstrapper emits new actions and they are emits new states") {
                assertEquals(2, testStore.capturedStates.size)
                assertEquals(bootstrappedValue, testStore.state.counter)

                bootstrapper.simulateBootstrapperUpdate(101)

                assertEquals(3, testStore.capturedStates.size)
                assertEquals(101, testStore.state.counter)
            }

        }


        Scenario("Store job cancellation") {

            val bootstrappedValue = 100
            val bootstrapper = CounterStoreBootstrapper(bootstrappedValue)
            val testStore = CounterTestStoreFactory.build(bootstrapper = bootstrapper)

            lateinit var job: Job

            Given("Store bootstrapped") {
                job = testStore.testSubscribe()
                assertEquals(2, testStore.capturedStates.size)
                assertEquals(bootstrappedValue, testStore.state.counter)
            }

            When("Store's job is cancelled") {
                job.cancel()
            }

            Then("Scope's context became inactive") {
                assertFalse(
                    testStore.scope.coroutineContext.isActive,
                    "Scope is still active after cancellation"
                )
            }

            When("Bootstrapper produces new value") {
                bootstrapper.simulateBootstrapperUpdate(12)
            }

            Then("This value is not dispatched") {
                assertEquals(2, testStore.capturedStates.size)
            }

            When("New intent is dispatched") {
                testStore.dispatch(CounterTestIntent.Increment)
            }

            Then("Intent does not change the state") {
                assertEquals(2, testStore.capturedStates.size)
            }

        }

    }

})