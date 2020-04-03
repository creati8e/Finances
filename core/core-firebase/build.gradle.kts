plugins {
    id("com.android.library")
    id("kotlin-android")
    id("com.google.gms.google-services")
}

dependencies {
    implementation(Libraries.KOTLIN)
    // Firebase.
    api(Libraries.Infrastructure.FIRESTORE)
    api(Libraries.Infrastructure.GMS_AUTH)
    api(Libraries.Infrastructure.AUTHENTICATION)
}