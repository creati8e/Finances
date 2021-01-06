package serg.chuprin.finances.core.impl.data.mapper.base

/**
 * Created by Sergey Chuprin on 20.04.2020.
 */
internal interface ReverseModelMapper<S, D> {

    fun mapFrom(model: D): S?

}