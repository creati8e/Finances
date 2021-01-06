package serg.chuprin.finances.core.impl.data.mapper.base

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
internal interface ModelMapper<S, D> {

    fun mapTo(model: S): D?

}