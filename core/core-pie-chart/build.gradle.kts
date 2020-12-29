plugins {
    id("com.android.library")
    id("kotlin-android")
}

dependencies {
    implementation(Libraries.KOTLIN)

    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.APPCOMPAT)

    // Timber.
    implementation(Libraries.TIMBER)
}