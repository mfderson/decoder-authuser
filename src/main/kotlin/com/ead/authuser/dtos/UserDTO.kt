package com.ead.authuser.dtos

import com.ead.authuser.dtos.views.UserDTOView
import com.ead.authuser.validations.UsernameContraint
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonView
import java.util.UUID
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDTO(
    var id: UUID = UUID.randomUUID(),

    @field:NotBlank(groups = [UserDTOView.RegistrationPost::class])
    @field:Size(min = 4, max = 50, groups = [UserDTOView.RegistrationPost::class])
//    @UsernameContraint(groups = [UserDTOView.RegistrationPost::class])
    @JsonView(UserDTOView.RegistrationPost::class)
    var username: String = "",

    @field:NotBlank(groups = [UserDTOView.RegistrationPost::class])
    @field:Email(groups = [UserDTOView.RegistrationPost::class])
    @JsonView(UserDTOView.RegistrationPost::class)
    var email: String = "",

    @field:NotBlank(groups = [UserDTOView.RegistrationPost::class, UserDTOView.PasswordPut::class])
    @field:Size(min = 6, max = 20, groups = [UserDTOView.RegistrationPost::class, UserDTOView.PasswordPut::class])
    @JsonView(UserDTOView.RegistrationPost::class, UserDTOView.PasswordPut::class)
    var password: String = "",

    @field:NotBlank(groups = [UserDTOView.PasswordPut::class])
    @field:Size(min = 6, max = 20, groups = [UserDTOView.PasswordPut::class])
    @JsonView(UserDTOView.PasswordPut::class)
    var oldPassword: String = "",

    @JsonView(UserDTOView.RegistrationPost::class, UserDTOView.UserPut::class)
    var fullName: String = "",

    @JsonView(UserDTOView.RegistrationPost::class, UserDTOView.UserPut::class)
    var phoneNumber: String = "",

    @JsonView(UserDTOView.RegistrationPost::class, UserDTOView.UserPut::class)
    var cpf: String = "",

    @field:NotBlank(groups = [UserDTOView.ImagePut::class])
    @JsonView(UserDTOView.ImagePut::class)
    var imageUrl: String = ""
) {

}
