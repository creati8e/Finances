import serg.chuprin.finances.config.AppConfig

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("kotlin-android-extensions")
    kotlin("plugin.serialization") version BuildScript.Versions.KOTLIN_VER
}

android {
    buildTypes {
        maybeCreate(AppConfig.BuildTypes.DEV.name)
        maybeCreate(AppConfig.BuildTypes.DEBUG.name)
    }
    // Common debug menu implementation for 'dev' and 'debug' build types.
    sourceSets {
        getByName(AppConfig.BuildTypes.DEV.name).java.srcDir("src/common/kotlin")
        getByName(AppConfig.BuildTypes.DEBUG.name).java.srcDir("src/common/kotlin")
    }
}

fun DependencyHandler.implementationAll(
    dependencies: List<String>,
    configuration: (dependencyNotation: Any) -> Unit = { implementation(it) }
) {
    dependencies.forEach(configuration)
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf(".*jar"))))

    api(project(":core:core-api"))
    api(project(":core:core-firebase"))

    implementation(Libraries.KOTLIN)
    implementation(Libraries.KOTLIN_SERIALIZATION)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)

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

    // region DI.

    kapt(Libraries.Dagger.COMPILER)
    implementationAll(Libraries.Dagger.DEPENDENCIES)

    // endregion

    // Architecture components.
    implementationAll(Libraries.Android.Lifecycle.DEPENDENCIES)

    implementationAll(Libraries.Preferences.DEPENDENCIES)

    add("devImplementation", Libraries.Debug.DEBUG)
    add("debugImplementation", Libraries.Debug.DEBUG)
    releaseImplementation(Libraries.Debug.RELEASE)

}