package serg.chuprin.finances.feature.userprofile.presentation.model.store

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import serg.chuprin.finances.core.api.extensions.flow.flowOfSingleValue
import serg.chuprin.finances.core.api.presentation.model.builder.DataPeriodTypePopupMenuCellsBuilder
import serg.chuprin.finances.core.api.presentation.screen.arguments.CategoriesListScreenArguments
import serg.chuprin.finances.core.mvi.Consumer
import serg.chuprin.finances.core.mvi.executor.StoreActionExecutor
import serg.chuprin.finances.core.mvi.executor.emptyFlowAction
import serg.chuprin.finances.core.mvi.invoke
import serg.chuprin.finances.feature.userprofile.domain.usecase.ChangeUserDefaultDataPeriodUseCase
import serg.chuprin.finances.feature.userprofile.domain.usecase.LogOutUseCase
import serg.chuprin.finances.feature.userprofile.presentation.model.builder.UserProfileCellsBuilder
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 31.07.2020.
 */
class UserProfileActionExecutor @Inject constructor(
    private val logOutUseCase: LogOutUseCase,
    private val cellsBuilder: UserProfileCellsBuilder,
    private val changeUserDefaultDataPeriodUseCase: ChangeUserDefaultDataPeriodUseCase,
    private val dataPeriodTypePopupMenuCellsBuilder: DataPeriodTypePopupMenuCellsBuilder
) : StoreActionExecutor<UserProfileAction, UserProfileState, UserProfileEffect, UserProfileEvent> {

    override fun invoke(
        action: UserProfileAction,
        state: UserProfileState,
        eventConsumer: Consumer<UserProfileEvent>,
        actionsFlow: Flow<UserProfileAction>
    ): Flow<UserProfileEffect> {
        return when (action) {
            is UserProfileAction.UpdateUser -> {
                handleUpdateUserAction(action)
            }
            is UserProfileAction.ExecuteIntent -> {
                when (val intent = action.intent) {
                    UserProfileIntent.ClickOnLogOutButton -> {
                        handleClickOnLogOutButton(eventConsumer)
                    }
                    UserProfileIntent.ClickOnOnLogoutConfirmationButton -> {
                        handleClickOnOnLogoutConfirmationButton(eventConsumer)
                    }
                    is UserProfileIntent.ClickOnDashboardWidgetsSetup -> {
                        handleClickOnDashboardWidgetsSetupIntent(intent, eventConsumer)
                    }
                    is UserProfileIntent.ClickOnPeriod -> {
                        handleClickOnPeriodIntent(eventConsumer)
                    }
                    is UserProfileIntent.ClickOnPeriodTypeCell -> {
                        handleClickOnPeriodTypeCellIntent(intent)
                    }
                    UserProfileIntent.ClickOnCategoriesCell -> {
                        handleClickOnCategoriesCellIntent(eventConsumer)
                    }
                }
            }
        }
    }

    private fun handleClickOnCategoriesCellIntent(
        eventConsumer: Consumer<UserProfileEvent>
    ): Flow<UserProfileEffect> {
        return emptyFlowAction {
            val arguments = CategoriesListScreenArguments.Editing
            eventConsumer(UserProfileEvent.NavigateToCategoriesListScreen(arguments))
        }
    }

    private fun handleClickOnPeriodTypeCellIntent(
        intent: UserProfileIntent.ClickOnPeriodTypeCell
    ): Flow<UserProfileEffect> {
        return flow {
            changeUserDefaultDataPeriodUseCase.execute(intent.periodTypePopupMenuCell.periodType)
        }
    }

    private fun handleClickOnPeriodIntent(
        eventConsumer: Consumer<UserProfileEvent>
    ): Flow<UserProfileEffect> {
        return emptyFlowAction {
            val menuCells = dataPeriodTypePopupMenuCellsBuilder.build()
            eventConsumer(UserProfileEvent.ShowPeriodTypesPopupMenu(menuCells))
        }
    }

    private fun handleClickOnDashboardWidgetsSetupIntent(
        intent: UserProfileIntent.ClickOnDashboardWidgetsSetup,
        eventConsumer: Consumer<UserProfileEvent>
    ): Flow<UserProfileEffect> {
        return emptyFlowAction {
            eventConsumer(
                UserProfileEvent.NavigateToDashboardWidgetsSetupScreen(intent.transitionName)
            )
        }
    }

    private fun handleClickOnOnLogoutConfirmationButton(
        eventConsumer: Consumer<UserProfileEvent>
    ): Flow<UserProfileEffect> {
        return flowOfSingleValue {
            logOutUseCase.execute()
            eventConsumer(UserProfileEvent.NavigateToLoginScreen)
            UserProfileEffect.LoggedOut
        }
    }

    private fun handleClickOnLogOutButton(
        eventConsumer: Consumer<UserProfileEvent>
    ): Flow<UserProfileEffect> {
        return emptyFlowAction {
            eventConsumer(UserProfileEvent.ShowLogoutConfirmDialog)
        }
    }

    private fun handleUpdateUserAction(
        action: UserProfileAction.UpdateUser
    ): Flow<UserProfileEffect> {
        return flowOfSingleValue {
            UserProfileEffect.CellsUpdated(cellsBuilder.build(action.user))
        }
    }

}