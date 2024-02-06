package com.ourproject.gojekclone.ui.decorator

import com.ourproject.register_domain.SubmitResult
import com.ourproject.register_domain.RegisterUseCase
import com.ourproject.register_domain.RegisterSubmitDomain
import com.ourproject.register_domain.UserDataDomain
import com.ourproject.register_domain.UserSessionUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RegisterDecorator(
    private val decorator: RegisterUseCase,
    private val local: UserSessionUseCase
) : RegisterUseCase {

    override fun register(registerSubmitDomain: RegisterSubmitDomain): Flow<SubmitResult> {
        return flow{
            decorator.register(registerSubmitDomain).collect{ result ->
                if (result is SubmitResult.Success) {
                    val interceptResult = UserDataDomain(
                        profilePhotoPath = result.data.profilePhotoPath,
                        address = result.data.address,
                        city = result.data.city,
                        roles = result.data.roles,
                        houseNumber = result.data.houseNumber,
                        createdAt = result.data.createdAt,
                        emailVerifiedAt = result.data.emailVerifiedAt,
                        currentTeamId = result.data.currentTeamId,
                        phoneNumber = result.data.phoneNumber,
                        updatedAt = result.data.updatedAt,
                        name = result.data.name,
                        id = result.data.id,
                        profilePhotoUrl = result.data.profilePhotoUrl,
                        email = result.data.email
                    )
                    local.insertUserSession(interceptResult)
                }
                emit(result)
            }
        }
    }
}