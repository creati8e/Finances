plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    kotlin("plugin.serialization") version BuildScript.Versions.KOTLIN_VER
}

fun DependencyHandler.implementationAll(
    dependencies: List<String>,
    configuration: (dependencyNotation: Any) -> Unit = { implementation(it) }
) {
    dependencies.forEach(configuration)
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf(".*jar"))))

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

    // Architecture components.
    implementationAll(Libraries.Android.Lifecycle.DEPENDENCIES)

    // Timber.
    implementation(Libraries.TIMBER)

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