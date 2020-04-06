plugins {
    `kotlin-dsl`
}

repositories {
    google()
    jcenter()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation("com.android.tools.build:gradle:4.0.0-beta03")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.71")
    implementation("de.mannodermaus.gradle.plugins:android-junit5:1.6.0.0")
    implementation("com.vanniktech:gradle-dependency-graph-generator-plugin:0.5.0")
}