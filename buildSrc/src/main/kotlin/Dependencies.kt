import BuildScript.Versions.KOTLIN_VER
import BuildScript.Versions.NAVIGATION_VER
import Libraries.Coroutines.VER

/**
 * Created by Sergey Chuprin on 16.03.2019.
 */
object BuildScript {

    object Versions {
        const val KOTLIN_VER = "1.4.10"
        const val NAVIGATION_VER = "2.3.2"
    }

    object Plugins {
        const val GMS = "com.google.gms:google-services:4.3.4"
        const val ANDROID = "com.android.tools.build:gradle:4.1.1"
        const val JUNIT5 = "de.mannodermaus.gradle.plugins:android-junit5:1.7.0.0"
        const val KOTLIN = "org.jetbrains.kotlin:kotlin-gradle-plugin:$KOTLIN_VER"
        const val NAVIGATION =
            "androidx.navigation:navigation-safe-args-gradle-plugin:$NAVIGATION_VER"
        const val GRAPH_VISUALIZER =
            "com.vanniktech:gradle-dependency-graph-generator-plugin:0.5.0"
        const val PROGUARD_GENERATOR =
            "gradle.plugin.ru.cleverpumpkin.proguard-dictionaries-generator:plugin:1.0.8"
    }

}

object Libraries {

    const val COIL = "io.coil-kt:coil:1.1.0"
    const val TIMBER = "com.github.ajalt:timberkt:1.5.1"
    const val FLEXBOX = "com.google.android:flexbox:2.0.1"
    const val EDGE_TO_EDGE = "de.halfbit:edge-to-edge:1.0-rc1"
    const val JAVAX_ANNOTATIONS = "javax.inject:javax.inject:1"
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$KOTLIN_VER"
    const val PAGE_INDICATOR = "ru.tinkoff.scrollingpagerindicator:scrollingpagerindicator:1.2.1"
    const val KOTLIN_SERIALIZATION = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1"

    object Android {

        object Lifecycle : DependenciesCollection {

            private const val VER = "2.3.0-rc01"
            private const val ARTIFACT = "androidx.lifecycle"

            override fun invoke(): Collection<String> {
                return listOf(
                    "$ARTIFACT:lifecycle-common-java8:$VER",
                    "$ARTIFACT:lifecycle-extensions:2.2.0",
                    "$ARTIFACT:lifecycle-viewmodel-ktx:$VER",
                    "$ARTIFACT:lifecycle-livedata-ktx:$VER"
                )
            }

        }

        const val CORE = "androidx.core:core-ktx:1.5.0-beta01"

        const val APPCOMPAT = "androidx.appcompat:appcompat:1.3.0-beta01"
        const val FRAGMENT = "androidx.fragment:fragment-ktx:1.3.0-rc01"
        const val TRANSITION = "androidx.transition:transition-ktx:1.4.0-rc01"
        const val VIEWPAGER = "androidx.viewpager2:viewpager2:1.1.0-alpha01"
        const val DESIGN = "com.google.android.material:material:1.3.0-rc01"
        const val RECYCLER_VIEW = "androidx.recyclerview:recyclerview:1.2.0-beta01"
        const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:2.0.2"

        object Navigation : DependenciesCollection {

            private const val ARTIFACT = "androidx.navigation"

            override fun invoke(): Collection<String> {
                return listOf(
                    "$ARTIFACT:navigation-ui-ktx:$NAVIGATION_VER",
                    "$ARTIFACT:navigation-runtime:$NAVIGATION_VER",
                    "$ARTIFACT:navigation-fragment-ktx:$NAVIGATION_VER"
                )
            }

        }

    }

    object Adapter : DependenciesCollection {

        private const val VER = "1.3.0"

        override fun invoke(): Collection<String> = listOf(
            "serg.chuprin:multiviewadapter:$VER",
            "serg.chuprin:multiviewadapter-kt-extensions:$VER"
        )

    }

    object Preferences {

        const val LIBRARY = "com.afollestad.rxkprefs:core:2.0.3"
    }

    object Coroutines {

        const val VER = "1.4.2"

        const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VER"
        const val ANDROID = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$VER"

        object Bindings : DependenciesCollection {

            private const val VER = "1.0.0"

            override fun invoke(): Collection<String> {
                return listOf(
                    "io.github.reactivecircus.flowbinding:flowbinding-android:$VER",
                    "io.github.reactivecircus.flowbinding:flowbinding-material:$VER"
                )
            }

        }

    }

    object Dagger {

        private const val VER = "2.30.1"

        const val LIBRARY = "com.google.dagger:dagger:$VER"
        const val COMPILER = "com.google.dagger:dagger-compiler:$VER"

    }

    object Infrastructure {
        const val AUTH = "com.google.firebase:firebase-auth:20.0.2"
        const val FIRESTORE = "com.google.firebase:firebase-firestore-ktx:22.0.1"
        const val GMS_AUTH = "com.google.android.gms:play-services-auth:19.0.0"
        const val PLAY_SERVICES_KTX = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.4.2"
    }

    object DebugMenu {
        private const val VER = "2.4.4"

        const val DEBUG = "com.github.pandulapeter.beagle:ui-drawer:$VER"
        const val RELEASE = "com.github.pandulapeter.beagle:noop:$VER"

    }

    object Tests {

        private const val JUNIT_VER = "5.7.0"
        private const val SPEK_VER = "2.0.15"

        const val MOCKK = "io.mockk:mockk:1.9.3"
        const val STRIKT = "io.strikt:strikt-core:0.28.1"

        // It's required for Strikt.
        const val FILE_PEEK = "com.christophsturm:filepeek:0.1.2"
        const val ASSERTIONS = "org.jetbrains.kotlin:kotlin-test:$KOTLIN_VER"
        const val SPEK_JVM = "org.spekframework.spek2:spek-dsl-jvm:$SPEK_VER"
        const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${VER}"
        const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$KOTLIN_VER"
        const val SPEK_RUNNER = "org.spekframework.spek2:spek-runner-junit5:$SPEK_VER"
        const val COROUTINES_DEBUG = "org.jetbrains.kotlinx:kotlinx-coroutines-debug:${VER}"

        const val JUPITER_API = "org.junit.jupiter:junit-jupiter-api:$JUNIT_VER"
        const val JUPITER_ENGINE = "org.junit.jupiter:junit-jupiter-engine:$JUNIT_VER"

    }

}