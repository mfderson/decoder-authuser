package com.ead.authuser.models

import com.fasterxml.jackson.annotation.JsonInclude
import java.io.Serializable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "USERS_COURSES")
@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserCourseModel(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    var user: UserModel = UserModel(),

    @Column(nullable = false)
    var courseId: UUID = UUID.randomUUID()
): Serializable