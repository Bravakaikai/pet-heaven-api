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
        val equipmentList = repo.findAll()
        if (equipmentList.isEmpty()) {
            seedData()
        }

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

    fun seedData() {
        val equipmentList = mutableListOf(
            Equipment(
                name = "咖哩飯",
                description = "體力+20",
                price = 30,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_curry_and_rice_01.png"
            ),
            Equipment(
                name = "蛋包飯",
                description = "體力+20",
                price = 30,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_omeletterice_01.png"
            ),
            Equipment(
                name = "吐司",
                description = "體力+10",
                price = 15,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_plain_bread_01.png"
            ),
            Equipment(
                name = "披薩",
                description = "體力+15",
                price = 20,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_pizza_01.png"
            ),
            Equipment(
                name = "玉米",
                description = "體力+10",
                price = 15,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_corn_01.png"
            ),
            Equipment(
                name = "薯條",
                description = "體力+5",
                price = 10,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_french_fries_01.png"
            ),
            Equipment(
                name = "西瓜",
                description = "體力+3",
                price = 5,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_watermelon_01.png"
            ),
            Equipment(
                name = "剉冰",
                description = "體力+3",
                price = 5,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_shaved_ice_01.png"
            ),
            Equipment(
                name = "布丁",
                description = "體力+5",
                price = 10,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_pudding_01.png"
            ),
            Equipment(
                name = "起司蛋糕",
                description = "體力+5",
                price = 10,
                imgUrl = "https://dotown.maeda-design-room.net/wp-content/uploads/2022/01/food_cheesecake_01.png"
            ),
        )
        repo.saveAll(equipmentList)
    }
}