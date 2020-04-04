package serg.chuprin.finances.core.api.domain.model

import java.util.UUID

/**
 * Created by Sergey Chuprin on 03.04.2020.
 */
inline class UUID(
    val value: String
) {

    val asJavaUUID: UUID
        get() = UUID.fromString(value)

}