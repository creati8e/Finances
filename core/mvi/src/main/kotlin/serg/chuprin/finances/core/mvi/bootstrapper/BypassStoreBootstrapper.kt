package serg.chuprin.finances.core.mvi.bootstrapper

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

/**
 * Created by Sergey Chuprin on 01.09.2019.
 */
class BypassStoreBootstrapper<A : Any> : StoreBootstrapper<A> {

    override fun invoke(): Flow<A> = emptyFlow()

}