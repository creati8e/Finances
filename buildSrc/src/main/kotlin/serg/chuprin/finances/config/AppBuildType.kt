package serg.chuprin.finances.config

/**
 * Created by Sergey Chuprin on 07.06.2019.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused", "CanBeParameter")
class AppBuildType(val name: String) {
    val implementation = "${name}Implementation"
}