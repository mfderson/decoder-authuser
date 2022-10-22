package com.ead.authuser.controllers

import com.ead.authuser.dtos.UserDTO
import com.ead.authuser.dtos.views.UserDTOView
import com.ead.authuser.models.UserModel
import com.ead.authuser.services.UserService
import com.ead.authuser.specifications.SpecificationTemplate
import com.fasterxml.jackson.annotation.JsonView
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/users")
class UserController(val service: UserService) {

    @GetMapping
    fun getAllUsers(
        spec: SpecificationTemplate.UserSpec,
        @PageableDefault(
            page = 0,
            size = 10,
            sort = ["id"],
            direction = Sort.Direction.ASC
        ) pageable: Pageable): ResponseEntity<Page<UserModel>> {
        val userPage: Page<UserModel> = service.findAll(spec, pageable)
        userPage.toList().forEach {
            val selfLink = linkTo(UserController::class.java).slash(it.id).withSelfRel()
            it.add(selfLink)
        }

        return ResponseEntity.status(HttpStatus.OK).body(userPage)
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID) = service.findById(id)

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: UUID) =
        service.findById(id)

    @PutMapping("/{id}")
    fun updateUser(
        @PathVariable id: UUID,
        @RequestBody @Validated(UserDTOView.UserPut::class) @JsonView(UserDTOView.UserPut::class) userDTO: UserDTO
    ) = service.updateUserData(id, userDTO)

    @PutMapping("/{id}/password")
    fun updatePassword(
        @PathVariable id: UUID,
        @RequestBody @Validated(UserDTOView.PasswordPut::class) @JsonView(UserDTOView.PasswordPut::class) userDTO: UserDTO
    ) = service.updatePassword(id, userDTO)

    @PutMapping("/{id}/image")
    fun updateImage(
        @PathVariable id: UUID,
        @RequestBody @Validated(UserDTOView.ImagePut::class) @JsonView(UserDTOView.ImagePut::class) userDTO: UserDTO
    ) = service.updateImage(id, userDTO)

//    String inputJson = "{\"name\":\"Jake\",\"salary\":3000,"
//    + "\"address\":{\"street\":\"101 Blue Dr\",\"city\":\"White Smoke\"}}";
//    System.out.println("input json: " + inputJson);
//
//    Employee existingEmployee = Employee.of("John", "Dev", 1000, "222-222-222",
//    Address.of("101 Blue Dr", "SunBridge", "23456"));
//    System.out.println("existing object: " + existingEmployee);
//    System.out.println("existing object hashCode: " + System.identityHashCode(existingEmployee));
//    System.out.println("existing nested object 'address' hashCode: " + System
//    .identityHashCode(existingEmployee.getAddress()));
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    ObjectReader objectReader = objectMapper.readerForUpdating(existingEmployee);
//    Employee updatedEmployee = objectReader.readValue(inputJson);
//    System.out.println("updated object: " + updatedEmployee);
//    System.out.println("updated object hashCode: " + System.identityHashCode(updatedEmployee));
//    System.out.println("updated nested object 'address' hashCode: " + System
//    .identityHashCode(updatedEmployee.getAddress()));
}