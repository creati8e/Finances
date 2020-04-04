package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.api.domain.gateway.AuthenticationGateway
import serg.chuprin.finances.core.impl.data.gateway.AuthenticationGatewayImpl

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@Module
internal abstract class CoreGatewaysModule {

    @[Binds AppScope]
    abstract fun bindsAuthGateway(impl: AuthenticationGatewayImpl): AuthenticationGateway

}