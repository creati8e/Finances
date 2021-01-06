plugins {
    id("com.android.library")
    id("kotlin-android")
}

dependencies {
    implementation(Libraries.KOTLIN)
    implementation(Libraries.Coroutines.CORE)
    api(Libraries.Tests.COROUTINES)
    implementation(Libraries.Tests.COROUTINES_DEBUG)

    // Timber.
    implementation(Libraries.TIMBER)

}