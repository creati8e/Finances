package serg.chuprin.finances.core.api.domain.di

import serg.chuprin.finances.core.api.domain.gateway.AuthenticationGateway

/**
 * Created by Sergey Chuprin on 02.04.2020.
 */
interface CoreGatewaysProvider {

    val authenticationGateway: AuthenticationGateway

}