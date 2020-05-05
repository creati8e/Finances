import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Created by Sergey Chuprin on 05.05.2020.
 */
fun DependencyHandler.implementationAll(
    dependencies: List<String>,
    configuration: (dependencyNotation: Any) -> Unit = { add("implementation", it) }
) {
    dependencies.forEach(configuration)
}