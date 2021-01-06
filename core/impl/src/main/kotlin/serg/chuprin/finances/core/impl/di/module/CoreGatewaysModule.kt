package serg.chuprin.finances.core.impl.di.module

import dagger.Binds
import dagger.Module
import serg.chuprin.finances.core.api.di.scopes.AppScope
import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway
import serg.chuprin.finances.core.impl.data.gateway.AuthorizationGatewayImpl

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
@Module
internal interface CoreGatewaysModule {

    @[Binds AppScope]
    fun bindsAuthGateway(impl: AuthorizationGatewayImpl): AuthorizationGateway

}