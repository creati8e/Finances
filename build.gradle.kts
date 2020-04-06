import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.TestedExtension
import com.android.build.gradle.internal.dsl.BuildType
import com.android.build.gradle.internal.dsl.TestOptions
import com.vanniktech.dependency.graph.generator.DependencyGraphGeneratorExtension
import com.vanniktech.dependency.graph.generator.DependencyGraphGeneratorPlugin
import de.mannodermaus.gradle.plugins.junit5.junitPlatform
import guru.nidi.graphviz.attribute.Color
import guru.nidi.graphviz.attribute.Style
import guru.nidi.graphviz.model.MutableNode
import org.jetbrains.kotlin.gradle.internal.AndroidExtensionsExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import serg.chuprin.finances.config.AppConfig

plugins {
    id("com.github.ben-manes.versions") version ("0.28.0")
}

buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
    }
    dependencies {
        classpath(BuildScript.Plugins.GMS)
        classpath(BuildScript.Plugins.KOTLIN)
        classpath(BuildScript.Plugins.JUNIT5)
        classpath(BuildScript.Plugins.ANDROID)
        classpath(BuildScript.Plugins.APP_BADGE)
        classpath(BuildScript.Plugins.GRAPH_VISUALIZER)
        classpath(BuildScript.Plugins.PROGUARD_GENERATOR)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("http://jitpack.io/")
    }
}

subprojects {
    addKotlinCompilerFlags()
    forceDependencyVersions()
    afterEvaluate {
        extensions
            .findByType(TestedExtension::class.java)
            ?.apply {
                enableExperimentalKotlinExtensions()
                defaultConfig {
                    versionCode = AppConfig.VERSION_CODE
                    versionName = AppConfig.VERSION_NAME
                    minSdkVersion(AppConfig.MIN_SDK)
                    targetSdkVersion(AppConfig.TARGET_SDK)
                }
                configureBuildTypes()

                compileSdkVersion(AppConfig.TARGET_SDK)
                buildToolsVersion("29.0.3")

                sourceSets.forEach { sourceSet ->
                    sourceSet.java.srcDir("src/${sourceSet.name}/kotlin")
                }

                with(compileOptions) {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
                configureSpek(this)
            }
    }
}

/**
 * Configure test options required for running Spek on android and add tests dependencies.
 */
fun Project.configureSpek(testedExtension: TestedExtension) {
    plugins.apply("de.mannodermaus.android-junit5")
    with(testedExtension) {
        testOptions {
            junitPlatform.filters.includeEngines("spek2")
            unitTests(delegateClosureOf<TestOptions.UnitTestOptions> {

                all(
                    KotlinClosure1<Any, Test>(
                        {
                            (this as Test).apply {
                                systemProperty("kotlinx.coroutines.debug", "on")
                                testLogging.setEvents(setOf("passed", "skipped", "failed"))
                            }
                        },
                        this
                    )
                )
            })
        }
    }
    dependencies {
        add("testImplementation", Libraries.Tests.JUPITER_API)
        add("testRuntimeOnly", Libraries.Tests.JUPITER_ENGINE)

        add("testImplementation", Libraries.Tests.SPEK_JVM)
        add("testImplementation", Libraries.Tests.SPEK_RUNNER)
        add("testImplementation", Libraries.Tests.KOTLIN_REFLECT)

        add("testImplementation", Libraries.Tests.MOCKK)
        add("testImplementation", Libraries.Tests.STRIKT)
        add("testImplementation", Libraries.Tests.ASSERTIONS)

        add("testImplementation", Libraries.Tests.COROUTINES)
        add("testImplementation", Libraries.Tests.COROUTINES_DEBUG)
    }
}

// Set build types for android module.
fun TestedExtension.configureBuildTypes() {

    fun BuildType.configProguard(isLibrary: Boolean): BuildType {
        return if (isLibrary) {
            consumerProguardFile(File("proguard-rules.pro"))
        } else {
            proguardFiles(
                File("proguard-rules.pro"),
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
        }
    }

    val isLibrary = this is LibraryExtension

    buildTypes {
        maybeCreate(AppConfig.BuildTypes.RELEASE.name).apply {
            isMinifyEnabled = true
            isDebuggable = false
            configProguard(isLibrary)
        }
        maybeCreate(AppConfig.BuildTypes.DEBUG.name).apply {
            isMinifyEnabled = true
            isDebuggable = false
            configProguard(isLibrary)
        }
        maybeCreate(AppConfig.BuildTypes.DEV.name).apply {
            isDebuggable = true
        }
    }
}

fun Project.addKotlinCompilerFlags() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
            kotlinOptions.freeCompilerArgs += listOf(
                "-XXLanguage:+InlineClasses",
                "-Xopt-in=kotlin.RequiresOptIn",
                "-Xuse-experimental=kotlin.ExperimentalStdlibApi",
                "-Xuse-experimental=kotlinx.coroutines.ObsoleteCoroutinesApi",
                "-Xuse-experimental=kotlinx.coroutines.ExperimentalCoroutinesApi"
            )
        }
    }
}

fun Project.enableExperimentalKotlinExtensions() {
    extensions.findByType(AndroidExtensionsExtension::class)?.isExperimental = true
}

fun Project.forceDependencyVersions() {
    configurations.all {
        resolutionStrategy {
            force(Libraries.KOTLIN)
        }
    }
}

plugins.apply(DependencyGraphGeneratorPlugin::class)

// Task name is generateDependencyGraphModules.
val modulesGenerator = DependencyGraphGeneratorExtension.Generator(
    name = "Modules",
    children = { false },
    include = { dependency -> dependency.moduleGroup.startsWith("finances", ignoreCase = true) },
    dependencyNode = { node: MutableNode, _: ResolvedDependency ->
        node.add(Style.FILLED, Color.rgb("#FFCB2B"))
    }
)
extensions.getByType(DependencyGraphGeneratorExtension::class).generators = listOf(modulesGenerator)

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}