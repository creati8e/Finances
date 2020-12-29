import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Created by Sergey Chuprin on 29.12.2020.
 */
interface DependenciesCollection {
    operator fun invoke(): Collection<String>
}

fun DependencyHandler.implementation(
    collection: DependenciesCollection,
    configuration: (dependencyNotation: Any) -> Unit = { add("implementation", it) }
) {
    collection().forEach(configuration)
}