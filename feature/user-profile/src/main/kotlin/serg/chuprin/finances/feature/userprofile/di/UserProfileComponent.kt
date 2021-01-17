package serg.chuprin.finances.feature.userprofile.di

import dagger.Component
import serg.chuprin.finances.core.api.di.scopes.ScreenScope
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.InjectableComponent
import serg.chuprin.finances.core.api.presentation.model.viewmodel.extensions.ViewModelComponent
import serg.chuprin.finances.feature.userprofile.presentation.model.viewmodel.UserProfileViewModel
import serg.chuprin.finances.feature.userprofile.presentation.view.UserProfileFragment

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
@ScreenScope
@Component(
    modules = [UserProfileModule::class],
    dependencies = [UserProfileDependencies::class]
)
interface UserProfileComponent :
    ViewModelComponent<UserProfileViewModel>,
    InjectableComponent<UserProfileFragment> {

    companion object {

        fun get(dependencies: UserProfileDependencies): UserProfileComponent {
            return DaggerUserProfileComponent
                .builder()
                .userProfileDependencies(dependencies)
                .build()
        }

    }

}