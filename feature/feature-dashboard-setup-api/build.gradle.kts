plugins {
    id("com.android.library")
    id("kotlin-android")
}

dependencies {
    implementation(project(":core:core-api"))

    implementation(Libraries.KOTLIN)
}