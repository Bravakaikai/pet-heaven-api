package com.example.petheaven.service

import com.example.petheaven.model.*
import com.example.petheaven.repository.EquipmentRepository
import com.example.petheaven.repository.UserEquipmentRepository
import com.example.petheaven.repository.UserRepository
import com.example.petheaven.vo.BagVo
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserEquipmentService(
    val repo: UserEquipmentRepository,
    val userRepo: UserRepository,
    val equipmentRepo: EquipmentRepository
) {
    fun getUserEquipmentList(userId: Long): Result {
        val bagVoList = userRepo.getById(userId).bagList?.map {
            val eq = it.equipment
            eq?.let { it1 -> BagVo(id = it1.id, name = eq.name, description = eq.description, price = eq.price, imgUrl = eq.imgUrl, amount = it.amount) }
        }
        return Result(message = bagVoList)
    }

    @Transactional
    fun buy(userEquipment: UserEquipment): Result {
        var result = Result()

        val equipment = equipmentRepo.getById(userEquipment.equipmentId)
        val user = userRepo.getById(userEquipment.userId)
        val balance = user.wallet - (equipment.price * userEquipment.amount)

        if (balance < 0) {
            result = Result("error", "Insufficient balance")
        } else {
            val storage = repo.findByUserIdAndEquipmentId(userEquipment.userId, userEquipment.equipmentId)

            // purchase again
            if (storage != null) {
                userEquipment.amount += storage.amount
                userEquipment.createdDate = storage.createdDate
            }
            repo.save(userEquipment)

            user.wallet = balance
            userRepo.save(user)
        }

        return result
    }

    fun eat(userEquipment: UserEquipment): Result {
        var result = Result()

        val storage = repo.findByUserIdAndEquipmentId(userEquipment.userId, userEquipment.equipmentId)

        if (storage == null) {
            result.status = "not found"
        } else {
            if (storage.amount <= 0) {
                result = Result("error", "Inventory shortage")
            } else {
                storage.amount -= 1
                if (storage.amount <= 0) {
                    repo.delete(storage)
                } else {
                    repo.save(storage)
                }
            }
        }

        return result
    }

    @Transactional
    fun sell(userEquipment: UserEquipment): Result {
        var result = Result()

        val storage = repo.findByUserIdAndEquipmentId(userEquipment.userId, userEquipment.equipmentId)
        val equipment = equipmentRepo.getById(userEquipment.equipmentId)

        if (storage == null) {
            result.status = "not found"
        } else {
            if (userEquipment.amount > storage.amount) {
                result = Result("error", "Out of stock")
            } else {
                storage.amount -= userEquipment.amount
                if (storage.amount <= 0) {
                    repo.delete(storage)
                } else {
                    repo.save(storage)
                }

                val user = userRepo.getById(userEquipment.userId)
                val cost = equipment.price * userEquipment.amount
                user.wallet += cost
                userRepo.save(user)
            }
        }

        return result
    }
}