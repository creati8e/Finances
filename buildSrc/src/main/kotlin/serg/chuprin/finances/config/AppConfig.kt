package serg.chuprin.finances.config

/**
 * Created by Sergey Chuprin on 07.06.2019.
 */
object AppConfig {

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0-alpha01"
    const val APPLICATION_ID = "serg.chuprin.finances"

    const val MIN_SDK = 21
    const val TARGET_SDK = 29

    object BuildTypes {

        val DEV = AppBuildType("dev")
        val DEBUG = AppBuildType("debug")
        val RELEASE = AppBuildType("release")

    }

}