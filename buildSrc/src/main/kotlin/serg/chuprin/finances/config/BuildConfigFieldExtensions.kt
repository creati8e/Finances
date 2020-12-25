package serg.chuprin.finances.config

import com.android.build.gradle.internal.dsl.BuildType

/**
 * Created by Sergey Chuprin on 25.12.2020.
 */
fun BuildType.setIsDebugMenuEnabled(enabled: Boolean) {
    buildConfigField("boolean", "IS_DEBUG_MENU_ENABLED", enabled.toString())
}