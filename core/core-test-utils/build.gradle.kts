plugins {
    id("com.android.library")
    id("kotlin-android")
}

dependencies {

    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf(".*jar"))))
    api(project(":core:core-api"))

    implementation(Libraries.KOTLIN)
    implementation(Libraries.KOTLIN_SERIALIZATION)
    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.JAVAX_ANNOTATIONS)
    api(Libraries.Tests.COROUTINES)
    implementation(Libraries.Tests.COROUTINES_DEBUG)

    // Timber.
    api(Libraries.TIMBER)

}