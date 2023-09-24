package com.ourproject.register_module.domain

import com.ourproject.register_module.datasource.http.dto.MetaDataEntity
import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity
import com.ourproject.register_module.datasource.http.dto.ResponseDataDto
import com.ourproject.register_module.datasource.http.dto.ResponseDataEntity
import com.ourproject.register_module.datasource.http.dto.UserDataEntity
import com.ourproject.register_module.datasource.http.dto.UserDto
import com.ourproject.register_module.datasource.http.dto.UserEntity
import com.ourproject.register_module.datasource.http.dto.UserLocal

class RegisterMapper {

    companion object{
        fun mapRegistrationEntityToData(entity: RegistrationEntity): RegistrationDto {
            return RegistrationDto(
                name = entity.name,
                email = entity.email,
                password = entity.password,
                password_confirmation = entity.password_confirmation,
                address = entity.address,
                city = entity.city,
                houseNumber = entity.houseNumber,
                phoneNumber = entity.phoneNumber
            )
        }

        fun mapRegistrationEntityToDto(entity: RegistrationEntity): RegistrationDto {
            return RegistrationDto(
                name = entity.name,
                email = entity.email,
                password = entity.password,
                password_confirmation = entity.password_confirmation,
                address = entity.address,
                city = entity.city,
                houseNumber = entity.houseNumber,
                phoneNumber = entity.phoneNumber
            )
        }

        fun mapRegistrationDtoToEntity(dto: RegistrationDto): RegistrationEntity {
            return RegistrationEntity(
                name = dto.name,
                email = dto.email,
                password = dto.password,
                password_confirmation = dto.password_confirmation,
                address = dto.address,
                city = dto.city,
                houseNumber = dto.houseNumber,
                phoneNumber = dto.phoneNumber
            )
        }

        fun mapUserDtoToRegistrationDto(dto: UserDto): RegistrationDto {
            return RegistrationDto(
                name = dto.name,
                email = dto.email,
                password = "",
                password_confirmation = "",
                address = dto.address,
                city = dto.city,
                houseNumber = dto.houseNumber,
                phoneNumber = dto.phoneNumber
            )
        }

        fun mapResponseDataDtoToEntity(dto: ResponseDataDto): ResponseDataEntity {
            val metaDataEntity = MetaDataEntity(
                code = dto.meta.code,
                status = dto.meta.status,
                message = dto.meta.message
            )

            val userDataEntity = UserDataEntity(
                access_token = dto.data.access_token,
                token_type = dto.data.token_type,
                user = mapUserDtoToEntity(dto.data.user)
            )

            return ResponseDataEntity(
                meta = metaDataEntity,
                data = userDataEntity
            )
        }

        fun mapUserDtoToEntity(dto: UserDto): UserEntity {
            return UserEntity(
                id = dto.id,
                name = dto.name,
                email = dto.email,
                address = dto.address,
                houseNumber = dto.houseNumber,
                phoneNumber = dto.phoneNumber,
                city = dto.city,
                profile_photo_url = dto.profile_photo_url
            )
        }

        fun mapUserEntityToUserLocal(entity: UserEntity): UserLocal {
            return UserLocal(
                id = entity.id,
                name = entity.name,
                email = entity.email,
                address = entity.address,
                houseNumber = entity.houseNumber,
                phoneNumber = entity.phoneNumber,
                city = entity.city,
            )
        }
    }
}