package serg.chuprin.finances.core.api.di.provider

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
interface CoreDependenciesProvider :
    CoreUtilsProvider,
    CoreGatewaysProvider,
    CoreManagerProvider,
    CoreServicesProvider,
    CoreUseCasesProvider,
    CoreBuildersProvider,
    CoreFormattersProvider,
    CoreRepositoriesProvider,
    CoreDataSourceProvider
