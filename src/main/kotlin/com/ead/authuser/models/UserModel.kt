package com.ead.authuser.models

import com.ead.authuser.dtos.UserEventDto
import com.ead.authuser.enums.UserStatus
import com.ead.authuser.enums.UserType
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.RepresentationModel
import java.io.Serializable
import java.time.LocalDateTime
import java.util.UUID
import javax.persistence.*

@Entity
@Table(name = "USERS")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserModel(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false, unique = true, length = 50)
    var username: String = "",

    @Column(nullable = false, unique = true, length = 50)
    var email: String = "",

    @Column(nullable = false, length = 255)
    @JsonIgnore
    var password: String = "",

    @Column(nullable = false, length = 150)
    var fullName: String = "",

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: UserStatus = UserStatus.ACTIVE,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: UserType = UserType.STUDENT,

    @Column(length = 20)
    var phoneNumber: String = "",

    @Column(length = 20)
    var cpf: String = "",

    @Column
    var imageUrl: String = "",

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    var creationDate: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    var lastUpdateDate: LocalDateTime = LocalDateTime.now(),

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")])
    val roles: MutableSet<RoleModel> = mutableSetOf()
    ): RepresentationModel<UserModel>(), Serializable {

}

fun UserModel.convertToUserEventDto(): UserEventDto {
    return UserEventDto(
        this.id,
        this.username,
        this.email,
        this.fullName,
        this.status.name,
        this.type.name,
        this.phoneNumber,
        this.cpf,
        this.imageUrl
    )
}