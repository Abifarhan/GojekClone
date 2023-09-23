package com.ourproject.register_module.domain

import com.ourproject.register_module.datasource.http.dto.RegistrationDto
import com.ourproject.register_module.datasource.http.dto.RegistrationEntity

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
    }
}