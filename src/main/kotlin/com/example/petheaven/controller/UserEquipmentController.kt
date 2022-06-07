package com.example.petheaven.controller

import com.example.petheaven.model.Result
import com.example.petheaven.model.UserEquipment
import com.example.petheaven.service.UserEquipmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/userEquipment")
class UserEquipmentController(val service: UserEquipmentService) {
    @GetMapping("/{userId}")
    fun index(@PathVariable userId: Long): ResponseEntity<Any?> {
        return try {
            ResponseEntity.ok(service.getUserEquipmentList(userId))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @PostMapping("/buy")
    fun buy(@RequestBody userEquipment: UserEquipment): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(service.buy(userEquipment))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @PostMapping("/eat")
    fun eat(@RequestBody userEquipment: UserEquipment): Any {
        return try {
            ResponseEntity.ok(service.eat(userEquipment))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @PostMapping("/sell")
    fun sell(@RequestBody userEquipment: UserEquipment): Any {
        return try {
            ResponseEntity.ok(service.sell(userEquipment))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }
}