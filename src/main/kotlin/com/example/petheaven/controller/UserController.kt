package com.example.petheaven.controller

import com.example.petheaven.vo.LoginVo
import com.example.petheaven.model.Result
import com.example.petheaven.model.User
import com.example.petheaven.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val service: UserService) {
    @GetMapping
    fun index(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(service.getUserList())
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @GetMapping("/{id}")
    fun info(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(service.getInfo(id))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody user: User): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(service.signup(user))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @PostMapping("/login")
    fun login(@RequestBody loginVo: LoginVo): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(service.login(loginVo))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @PutMapping("/editInfo")
    fun editInfo(@RequestBody user: User): Any {
        return try {
            ResponseEntity.ok(service.editInfo(user))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @GetMapping("/checkRoleValid/{id}")
    fun checkIsAdmin(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(service.checkIsAdmin(id))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }
}