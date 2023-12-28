package com.ourproject.login_http

import com.ourproject.login_domain.LoginData
import com.ourproject.login_domain.LoginSubmitEntity
import com.ourproject.login_domain.LoginSubmitResultEntity
import com.ourproject.login_domain.User

class LoginMapper {

    companion object{
        fun mapEntityToDto(loginSubmitEntity: LoginSubmitEntity): LoginSubmitDto{
            return LoginSubmitDto(
                email = loginSubmitEntity.email,
                password = loginSubmitEntity.password
            )
        }

        fun mapDtoToEntity(remoteLoginData: RemoteLoginData): LoginSubmitResultEntity {
            return LoginSubmitResultEntity(
                loginData = LoginData(
                    accessToken = remoteLoginData.accessToken,
                    tokenType = remoteLoginData.tokenType,
                    user =
                    User(
                        profilePhotoUrl = remoteLoginData.remoteUser.profilePhotoUrl,
                        address = remoteLoginData.remoteUser.address,
                        city = remoteLoginData.remoteUser.city,
                        roles = remoteLoginData.remoteUser.roles,
                        houseNumber = remoteLoginData.remoteUser.houseNumber,
                        createdAt = remoteLoginData.remoteUser.createdAt,
                        emailVerifiedAt = remoteLoginData.remoteUser.emailVerifiedAt,
                        currentTeamId = remoteLoginData.remoteUser.currentTeamId,
                        phoneNumber = remoteLoginData.remoteUser.phoneNumber,
                        updatedAt = remoteLoginData.remoteUser.updatedAt,
                        name = remoteLoginData.remoteUser.name,
                        id = remoteLoginData.remoteUser.id,
                        profilePhotoPath = remoteLoginData.remoteUser.profilePhotoPath,
                        email = remoteLoginData.remoteUser.email
                    )

                )
            )
        }
    }


}