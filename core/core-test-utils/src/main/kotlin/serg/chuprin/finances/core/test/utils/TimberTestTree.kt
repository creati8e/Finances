package serg.chuprin.finances.core.test.utils

import timber.log.Timber

/**
 * Created by Sergey Chuprin on 23.09.2019.
 */
class TimberTestTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        val list = listOfNotNull(
            tag?.let { "Tag: $it" },
            "Message: $message",
            t?.let { "Throwable: $t" }

        )
        println(list.joinToString("; ", transform = { it }))
    }

}