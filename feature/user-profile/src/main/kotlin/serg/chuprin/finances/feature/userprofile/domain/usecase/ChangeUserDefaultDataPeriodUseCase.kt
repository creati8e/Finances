package serg.chuprin.finances.feature.userprofile.domain.usecase

import serg.chuprin.finances.core.api.domain.model.period.DataPeriodType
import serg.chuprin.finances.core.api.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Created by Sergey Chuprin on 06.12.2020.
 */
class ChangeUserDefaultDataPeriodUseCase @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun execute(newDataPeriodType: DataPeriodType) {
        userRepository.updateUser(
            userRepository.getCurrentUser().copy(dataPeriodType = newDataPeriodType)
        )
    }

}