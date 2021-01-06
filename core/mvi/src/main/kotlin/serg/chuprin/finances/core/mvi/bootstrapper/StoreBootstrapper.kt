package serg.chuprin.finances.core.mvi.bootstrapper

import kotlinx.coroutines.flow.Flow

/**
 * Created by Sergey Chuprin on 09.08.2019.
 *
 * Bootstrapper it is a component which initializes a store.
 * For example if could load some data at store initialization.
 *
 * [A] - type of action this bootstrapper produces.
 */
typealias StoreBootstrapper<A> = () -> Flow<A>