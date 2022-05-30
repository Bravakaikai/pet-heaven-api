package com.example.petheaven.controller

import com.example.petheaven.model.UserEquipment
import com.example.petheaven.service.UserEquipmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class UserEquipmentController(val service: UserEquipmentService) {
    @GetMapping("/bag/{userId}")
    fun index(@PathVariable userId: Long): ResponseEntity<Any?> {
        return ResponseEntity.ok(service.getUserEquipmentList(userId))
    }

    @PostMapping("/buy")
    fun buy(@RequestBody userEquipment: UserEquipment): ResponseEntity<Any> {
        return ResponseEntity.ok(service.buy(userEquipment))
    }

    @PostMapping("/eat")
    fun eat(@RequestBody userEquipment: UserEquipment): Any {
        return ResponseEntity.ok(service.eat(userEquipment))
    }

    @PostMapping("/sell")
    fun sell(@RequestBody userEquipment: UserEquipment): Any {
        return ResponseEntity.ok(service.sell(userEquipment))
    }
}