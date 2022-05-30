package com.example.petheaven.controller

import com.example.petheaven.model.Login
import com.example.petheaven.model.User
import com.example.petheaven.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserController(val service: UserService) {
    @GetMapping("/user")
    fun index(): ResponseEntity<Any> {
        return ResponseEntity.ok(service.getUserList())
    }

    @GetMapping("/user/{id}")
    fun info(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(service.getInfo(id))
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody user: User): ResponseEntity<Any> {
        return ResponseEntity.ok(service.signup(user))
    }

    @PostMapping("/login")
    fun login(@RequestBody login: Login): ResponseEntity<Any> {
        return ResponseEntity.ok(service.login(login))
    }

    @PutMapping("/editInfo")
    fun editInfo(@RequestBody user: User): Any {
        return ResponseEntity.ok(service.editInfo(user))
    }

    @GetMapping("/checkRoleValid/{id}")
    fun checkIsAdmin(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(service.checkIsAdmin(id))
    }
}