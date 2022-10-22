package com.ead.authuser.specifications

import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.ead.authuser.models.UserModel
import org.springframework.data.jpa.domain.Specification

class UserSpecification {

    companion object {
        fun withTypeEqual(type: String?) =
            type?.let {
                Specification<UserModel> { root, query, criteriaBuilder ->
                    criteriaBuilder.equal(root.get<UserType>(UserModel::type.name), UserType.valueOf(type))
                }
            }

        fun withStatusEqual(status: String?) =
            status?.let {
                Specification<UserModel> { root, query, criteriaBuilder ->
                    criteriaBuilder.equal(root.get<UserStatus>(UserModel::status.name), UserStatus.valueOf(status))
                }
            }

        fun withEmailLike(email: String?) =
            email?.let {
                Specification<UserModel> { root, query, criteriaBuilder ->
                    criteriaBuilder.like(root.get(UserModel::email.name), "%$email%")
                }
            }
    }
}