package serg.chuprin.finances.core.api.domain.model

import java.util.*

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
inline class Id(
    val value: String
) {

    companion object {

        fun existing(value: String) = Id(value)

        fun createNew(): Id = Id(UUID.randomUUID().toString())

    }

}