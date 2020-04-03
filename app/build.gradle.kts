import serg.chuprin.finances.config.AppConfig

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    kotlin("plugin.serialization") version BuildScript.Versions.KOTLIN_VER
    id("ru.cleverpumpkin.badge")
    id("ru.cleverpumpkin.proguard-dictionaries-generator")
}

android {
    defaultConfig {
        versionCode = AppConfig.VERSION_CODE
        versionName = AppConfig.VERSION_NAME
        applicationId = AppConfig.APPLICATION_ID
    }
    buildTypes {
        maybeCreate(AppConfig.BuildTypes.DEBUG.name).apply {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
        }
        maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
            applicationIdSuffix = ".debug"
            signingConfig = signingConfigs.getByName(AppConfig.BuildTypes.DEBUG.name)
        }
        maybeCreate(AppConfig.BuildTypes.RELEASE.name).apply {
            setProperty(
                "archivesBaseName",
                "${AppConfig.APPLICATION_ID}_${AppConfig.VERSION_NAME}_${AppConfig.VERSION_CODE}"
            )
        }
    }
}

badge {
    iconNames = listOf("@mipmap/ic_launcher_foreground")
    buildTypes {
        create(AppConfig.BuildTypes.DEV.name) {
            enabled = true
            fontSize = 12
            text = AppConfig.VERSION_NAME
        }
        create(AppConfig.BuildTypes.DEBUG.name) {
            enabled = true
            fontSize = 12
            text = AppConfig.VERSION_NAME
        }
    }
}

proguardDictionaries {
    dictionaryNames = listOf(
        "build/class-dictionary",
        "build/method-dictionary",
        "build/package-dictionary"
    )
    minLineLength = 10
    maxLineLength = 20
}

fun DependencyHandler.implementationAll(
    dependencies: List<String>,
    configuration: (dependencyNotation: Any) -> Unit = { implementation(it) }
) {
    dependencies.forEach(configuration)
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf(".*jar"))))

    // region Modules.

    implementation(project(":core:core-impl"))
    implementation(project(":feature:feature-dashboard"))
    implementation(project(":feature:feature-authorization"))

    // endregion

    implementation(Libraries.KOTLIN)
    implementation(Libraries.KOTLIN_SERIALIZATION)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)

    // region DI.

    kapt(Libraries.Dagger.COMPILER)
    implementationAll(Libraries.Dagger.DEPENDENCIES)

    // endregion

    // region UI.

    implementation(Libraries.COIL)

    // Navigation.
    implementationAll(Libraries.Android.Navigation.DEPENDENCIES)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.APPCOMPAT)
    implementation(Libraries.Android.CONSTRAINT_LAYOUT)

    // endregion

    // Architecture components.
    implementationAll(Libraries.Android.Lifecycle.DEPENDENCIES)

    // Firebase.
    implementation(Libraries.Infrastructure.FIRESTORE)
    implementation(Libraries.Infrastructure.AUTHENTICATION)

    // Networking.
    implementationAll(Libraries.Network.DEPENDENCIES)

    // region Testing.

    testImplementation(Libraries.Tests.MOCKK)
    testImplementation(Libraries.Tests.STRIKT)
    testImplementation(Libraries.Tests.SPEK_JVM)
    testImplementation(Libraries.Tests.ASSERTIONS)
    testImplementation(Libraries.Tests.SPEK_RUNNER)
    testImplementation(Libraries.Tests.KOTLIN_REFLECT)

    // endregion

}