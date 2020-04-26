plugins {
    id("com.android.library")
    id("kotlin-android")
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf(".*jar"))))
    implementation(Libraries.KOTLIN)

    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.APPCOMPAT)

    // Timber.
    api(Libraries.TIMBER)

}