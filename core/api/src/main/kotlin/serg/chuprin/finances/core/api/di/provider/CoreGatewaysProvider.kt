package serg.chuprin.finances.core.api.di.provider

import serg.chuprin.finances.core.api.domain.gateway.AuthorizationGateway

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
interface CoreGatewaysProvider {

    val authorizationGateway: AuthorizationGateway

}