package serg.chuprin.finances.core.api.domain.model

import java.util.*

/**
 * Created by Sergey Chuprin on 04.04.2020.
 */
inline class Id(
    val value: String
) {

    companion object {

        val UNKNOWN = Id("UNKNOWN")

        fun existing(value: String) = Id(value)

        fun createNew(): Id = Id(UUID.randomUUID().toString())

    }

}

fun Id?.orUnknown(): Id = this ?: Id.UNKNOWN