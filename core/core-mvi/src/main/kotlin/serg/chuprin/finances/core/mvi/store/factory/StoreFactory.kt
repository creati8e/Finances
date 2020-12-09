package serg.chuprin.finances.core.mvi.store.factory

import serg.chuprin.finances.core.mvi.store.BaseStore

/**
 * Created by Sergey Chuprin on 08.12.2020.
 */
interface StoreFactory<I, S, E, STORE : BaseStore<I, S, E>> {

    fun create(): STORE

}