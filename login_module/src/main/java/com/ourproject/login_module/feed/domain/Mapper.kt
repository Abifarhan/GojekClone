package com.ourproject.login_module.feed.domain

import com.ourproject.login_module.feed.db.UserLocal
import com.ourproject.login_module.feed.http.LoginResultDto
import com.ourproject.login_module.feed.http.LoginSubmitDto

class Mapper {

    companion object{

        fun mapLoginSubmitEntityToDto(entity: LoginSubmitEntity): LoginSubmitDto {
            return LoginSubmitDto(
                email = entity.email,
                password = entity.password
            )
        }


        fun mapLoginResultDtoToEntity(dto: LoginResultDto): LoginResultEntity {
            val metaEntity = MetaEntity(
                code = dto.meta.code,
                status = dto.meta.status,
                message = dto.meta.message
            )

            val userEntity = UserEntity(
                id = dto.data.user.id,
                name = dto.data.user.name,
                email = dto.data.user.email,
                emailVerifiedAt = dto.data.user.email_verified_at,
                roles = dto.data.user.roles,
                currentTeamId = dto.data.user.current_team_id,
                houseNumber = dto.data.user.houseNumber,
                phoneNumber = dto.data.user.phoneNumber,
                city = dto.data.user.city,
                createdAt = dto.data.user.created_at.toInt(),
                updatedAt = dto.data.user.updated_at.toInt(),
                profilePhotoPath = dto.data.user.profile_photo_path,
                profilePhotoUrl = dto.data.user.profile_photo_url
            )

            val dataEntity = DataEntity(
                accessToken = dto.data.access_token,
                tokenType = dto.data.token_type,
                user = userEntity
            )

            return LoginResultEntity(
                meta = metaEntity,
                data = dataEntity
            )
        }


        fun mapUserEntityToLocal(entity: UserEntity): com.ourproject.register_module.datasource.http.dto.UserLocal {
            return com.ourproject.register_module.datasource.http.dto.UserLocal(
                id = entity.id,
                name = entity.name,
                email = entity.email,
                address = entity.profilePhotoPath ?: "default",
                houseNumber = entity.houseNumber,
                phoneNumber = entity.phoneNumber,
                city = entity.city
            )
        }
    }
}