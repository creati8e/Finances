plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

dependencies {
    implementation(project(":core:api"))
    implementation(Libraries.KOTLIN)

    implementation(Libraries.Coroutines.CORE)
    implementation(Libraries.Coroutines.ANDROID)

    implementation(Libraries.Adapter)

    // Android.
    implementation(Libraries.Android.CORE)
    implementation(Libraries.Android.DESIGN)
    implementation(Libraries.Android.FRAGMENT)
    implementation(Libraries.Android.APPCOMPAT)
    implementation(Libraries.Android.TRANSITION)
    implementation(Libraries.Android.CONSTRAINT_LAYOUT)
}