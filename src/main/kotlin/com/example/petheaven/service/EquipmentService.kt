package com.example.petheaven.service

import com.example.petheaven.model.Equipment
import com.example.petheaven.model.Result
import com.example.petheaven.repository.EquipmentRepository
import com.example.petheaven.repository.UserEquipmentRepository
import com.example.petheaven.vo.EquipmentVo
import org.springframework.stereotype.Service

@Service
class EquipmentService(
    val repo: EquipmentRepository,
    val userEquipmentRepo: UserEquipmentRepository,
) {
    fun getShopList(): Result {
        return Result("success", repo.findAll().map {
            it.convertToVo()
        })
    }

    fun getInfo(id: Long): Result {
        return Result(message = repo.getById(id))
    }

    fun getEquipmentList(): Result {
        return Result(message = repo.findAllByOrderByUpdatedDateDesc())
    }

    fun create(vo: EquipmentVo): Result {
        val equipment = Equipment(name = vo.name, description = vo.description, price = vo.price, imgUrl = vo.imgUrl)
        var result = Result()
        result = checkDuplicate(result, equipment)

        // no error
        if (result.status != "error") {
            repo.save(equipment)
        }
        return result
    }

    fun edit(id: Long, vo: EquipmentVo): Result {
        val equipment =
            Equipment(id = vo.id, name = vo.name, description = vo.description, price = vo.price, imgUrl = vo.imgUrl)

        var result = Result()

        if (repo.existsById(id)) {
            result = checkDuplicate(result, equipment)

            // no error
            if (result.status != "error") {
                val currentEquipment = repo.getById(id)
                equipment.createdDate = currentEquipment.createdDate
                repo.save(equipment)
            }
        } else {
            result.status = "not found"
        }

        return result
    }

    fun delete(id: Long): Result {
        val result = Result()

        if (repo.existsById(id)) {
            val userEquipment = userEquipmentRepo.findByEquipmentId(id)
            // userEquipment has data
            if (userEquipment != null) {
                result.status = "error"
            } else {
                repo.deleteById(id)
            }
        }
        return result
    }

    // check name & email is duplicate
    fun checkDuplicate(result: Result, equipment: Equipment): Result {
        val errMsg = mutableMapOf<String, String>()

        val nameIsExist: Equipment? = if (equipment.id != 0L) {
            // edit
            repo.findByNameAndIdIsNotNull(equipment.id, equipment.name)
        } else {
            // create
            repo.findByName(equipment.name)
        }

        if (nameIsExist != null) {
            errMsg["name"] = "Duplicate name"
        }

        if (errMsg.isNotEmpty()) {
            result.status = "error"
            result.message = errMsg
        }

        return result
    }
}