import BuildScript.Versions.KOTLIN_VER
import BuildScript.Versions.NAVIGATION_VER
import Libraries.Coroutines.VER

/**
 * Created by Sergey Chuprin on 16.03.2019.
 */
object BuildScript {

    object Versions {
        const val KOTLIN_VER = "1.3.71"
        const val NAVIGATION_VER = "2.3.0-alpha04"
    }

    object Plugins {
        const val GMS = "com.google.gms:google-services:4.3.3"
        const val APP_BADGE = "gradle.plugin.app-badge:plugin:1.0.2"
        const val ANDROID = "com.android.tools.build:gradle:4.0.0-beta04"
        const val JUNIT5 = "de.mannodermaus.gradle.plugins:android-junit5:1.6.0.0"
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VER"
        const val GRAPH_VISUALIZER =
            "com.vanniktech:gradle-dependency-graph-generator-plugin:0.5.0"
        const val PROGUARD_GENERATOR =
            "gradle.plugin.ru.cleverpumpkin.proguard-dictionaries-generator:plugin:1.0.8"
    }

}

object Libraries {

    const val COIL = "io.coil-kt:coil:0.9.5"
    const val TIMBER = "com.github.ajalt:timberkt:1.5.1"
    const val JAVAX_ANNOTATIONS = "javax.inject:javax.inject:1"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$KOTLIN_VER"
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-runtime:0.20.0"

    object Android {

        object Lifecycle {

            private const val VER = "2.3.0-alpha01"
            private const val ARTIFACT = "androidx.lifecycle"

            val DEPENDENCIES = listOf(
                "$ARTIFACT:lifecycle-common-java8:$VER",
                "$ARTIFACT:lifecycle-extensions:2.2.0",
                "$ARTIFACT:lifecycle-viewmodel-ktx:$VER",
                "$ARTIFACT:lifecycle-livedata-ktx:$VER"
            )
        }

        const val CORE = "androidx.core:core-ktx:1.3.0-beta01"

        const val VIEWPAGER = "androidx.viewpager2:viewpager2:1.0.0"
        const val APPCOMPAT = "androidx.appcompat:appcompat:1.2.0-beta01"
        const val FRAGMENT = "androidx.fragment:fragment-ktx:1.3.0-alpha03"
        const val DESIGN = "com.google.android.material:material:1.2.0-alpha05"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.0.0-beta4"

        object Navigation {

            private const val ARTIFACT = "androidx.navigation"

            val DEPENDENCIES = listOf(
                "$ARTIFACT:navigation-ui-ktx:$NAVIGATION_VER",
                "$ARTIFACT:navigation-runtime:$NAVIGATION_VER",
                "$ARTIFACT:navigation-fragment-ktx:$NAVIGATION_VER"
            )
        }

    }

    object Adapter {

        private const val VER = "1.3.0"

        private const val LIBRARY = "serg.chuprin:multiviewadapter:$VER"
        private const val EXTENSION = "serg.chuprin:multiviewadapter-kt-extensions:$VER"

        val DEPENDENCIES = listOf(LIBRARY, EXTENSION)

    }

    object Preferences {

        private const val VER = "2.0.3"
        private const val ARTIFACT = "com.afollestad.rxkprefs"

        val DEPENDENCIES = listOf("$ARTIFACT:core:$VER")
    }

    object Coroutines {

        const val VER = "1.3.5"

        const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VER"
        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VER"
    }

    object Dagger {

        private const val VER = "2.27"
        private const val ARTIFACT = "com.google.dagger"

        const val COMPILER = "$ARTIFACT:dagger-compiler:$VER"

        val LIBRARY = "$ARTIFACT:dagger:$VER"
        val DEPENDENCIES = listOf("$ARTIFACT:dagger:$VER")

    }

    object Infrastructure {
        const val AUTH = "com.google.firebase:firebase-auth:19.3.0"
        const val FIRESTORE = "com.google.firebase:firebase-firestore-ktx:21.4.2"
        const val GMS_AUTH = "com.google.android.gms:play-services-auth:17.0.0"
        const val PLAY_SERVICES_KTX = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.3.5"
    }

    object Network {

        private const val RETROFIT_VER = "2.8.1"

        private const val RETROFIT = "com.squareup.retrofit2:retrofit:$RETROFIT_VER"
        private const val OKHTTP_LOGGER = "com.github.ihsanbal:LoggingInterceptor:3.0.0"
        private const val KOTLIN_SERIALIZATION_CONVERTER =
            "com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:0.5.0"

        val DEPENDENCIES = listOf(RETROFIT, OKHTTP_LOGGER, KOTLIN_SERIALIZATION_CONVERTER)

    }

    object Tests {

        private const val JUNIT_VER = "5.6.0"
        private const val SPEK_VER = "2.0.10"

        const val MOCKK = "io.mockk:mockk:1.9.3"
        const val STRIKT = "io.strikt:strikt-core:0.25.0"
        const val ASSERTIONS = "org.jetbrains.kotlin:kotlin-test:$KOTLIN_VER"
        const val SPEK_JVM = "org.spekframework.spek2:spek-dsl-jvm:$SPEK_VER"
        const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:1.3.5"
        const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$KOTLIN_VER"
        const val SPEK_RUNNER = "org.spekframework.spek2:spek-runner-junit5:$SPEK_VER"
        const val COROUTINES_DEBUG = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:${VER}"

        const val JUPITER_API = "org.junit.jupiter:junit-jupiter-api:$JUNIT_VER"
        const val JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine:$JUNIT_VER"

    }

}