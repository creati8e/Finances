package serg.chuprin.finances.core.api.extensions.flow

/*
 * Copyright 2019 David Karnok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.AbstractFlow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicBoolean

/**
 * Consumes the main source until the other source emits an item or completes.
 */
@FlowPreview
internal class FlowTakeUntil<T>(
    private val source: Flow<T>,
    private val otherProvider: Flow<Any>
) : AbstractFlow<T>() {

    private companion object {
        private val STOP = StopException()
    }

    private class StopException : CancellationException()

    override suspend fun collectSafely(collector: FlowCollector<T>) {
        coroutineScope {
            val gate = AtomicBoolean()

            val stopCollectionJob = launch {
                try {
                    otherProvider.collect {
                        throw STOP
                    }
                } catch (ex: StopException) {
                    // Okay
                } finally {
                    gate.set(true)
                }
            }

            try {
                source.collect { value ->
                    if (gate.get()) {
                        throw STOP
                    }
                    collector.emit(value)

                }
            } catch (ex: StopException) {
                // Okay
            } finally {
                stopCollectionJob.cancel(STOP)
            }
        }
    }

}