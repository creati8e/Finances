package serg.chuprin.finances.feature.userprofile.presentation.model.store

import kotlinx.coroutines.flow.Flow
import serg.chuprin.finances.core.mvi.bootstrapper.StoreBootstrapper
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class UserProfileBootstrapper @Inject constructor(
) : StoreBootstrapper<UserProfileAction> {

    override fun invoke(): Flow<UserProfileAction> {
        TODO("Not yet implemented")
    }

}