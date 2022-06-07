package com.example.petheaven.controller

import com.example.petheaven.model.Result
import com.example.petheaven.service.EquipmentService
import com.example.petheaven.service.UserService
import com.example.petheaven.vo.EquipmentVo
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/equipment")
class EquipmentController(val service: EquipmentService, val userService: UserService) {
    @GetMapping("/shop")
    fun index(): ResponseEntity<Any> {
        return try {
            ResponseEntity.ok(service.getShopList())
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

    @GetMapping("/admin/{userId}")
    fun backstage(@PathVariable userId: Long): ResponseEntity<Any> {
        return try {
            checkIsAdmin(userId)
            ResponseEntity.ok(service.getEquipmentList())
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @PostMapping("/admin/create")
    fun create(@RequestBody equipmentVo: EquipmentVo): ResponseEntity<Any> {
        return try {
            checkIsAdmin(equipmentVo.userId)
            ResponseEntity.ok(service.create(equipmentVo))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @PutMapping("/admin/edit/{id}")
    fun edit(@PathVariable id: Long, @RequestBody equipmentVo: EquipmentVo): Any {
        return try {
            checkIsAdmin(equipmentVo.userId)
            ResponseEntity.ok(service.edit(id, equipmentVo))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    @DeleteMapping("/admin/delete/{userId}/{id}")
    fun delete(@PathVariable userId: Long, @PathVariable id: Long): Any {
        return try {
            checkIsAdmin(userId)
            ResponseEntity.ok(service.delete(id))
        } catch (exception: Exception) {
            ResponseEntity.ok(Result.errorResult())
        }
    }

    fun checkIsAdmin(userId : Long) {
        val isAdmin = userService.checkIsAdmin(userId).message
        if(isAdmin != true) throw Exception()
    }
}