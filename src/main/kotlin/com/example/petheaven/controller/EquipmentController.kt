package com.example.petheaven.controller

import com.example.petheaven.model.Equipment
import com.example.petheaven.service.EquipmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shop")
class EquipmentController(val service: EquipmentService) {
    @GetMapping
    fun index(): ResponseEntity<Any> {
        return ResponseEntity.ok(service.getShopList())
    }

    @GetMapping("/equipment")
    fun backstage(): ResponseEntity<Any> {
        return ResponseEntity.ok(service.getEquipmentList())
    }

    @GetMapping("/{id}")
    fun info(@PathVariable id: Long): ResponseEntity<Any> {
        return ResponseEntity.ok(service.getInfo(id))
    }

    @PostMapping("/create")
    fun create(@RequestBody equipment: Equipment): ResponseEntity<Any> {
        service.create(equipment)
        return ResponseEntity.ok(service.create(equipment))
    }

    @PutMapping("/edit")
    fun edit(@RequestBody equipment: Equipment): Any {
        return ResponseEntity.ok(service.edit(equipment))
    }

    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable id: Long): Any {
        return ResponseEntity.ok(service.delete(id))
    }
}