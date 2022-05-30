package com.example.petheaven.service

import com.example.petheaven.model.*
import com.example.petheaven.repository.EquipmentRepository
import com.example.petheaven.repository.UserEquipmentRepository
import com.example.petheaven.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserEquipmentService(
    val repo: UserEquipmentRepository,
    val userRepo: UserRepository,
    val equipmentRepo: EquipmentRepository
) {
    fun getUserEquipmentList(userId: Long): Result {
        var bagList = mutableListOf<Bag>()

        repo.findByIdUserId(userId)?.forEach {
            var eq = it?.id?.let { item -> equipmentRepo.getById(item.equipmentId) }
            val bag = eq?.let { eqItem -> it?.let { item -> Bag(eqItem.id, eq.name, eq.description, eq.price, eq.imgUrl, item.amount) } }
            if (bag != null) {
                bagList.add(bag)
            }
        }
        return Result(message = bagList)
    }

    fun buy(userEquipment: UserEquipment): Result {
        var result = Result()

        val equipment = equipmentRepo.getById(userEquipment.id.equipmentId)
        val user = userRepo.getById(userEquipment.id.userId)
        val balance = user.wallet - (equipment.price * userEquipment.amount)

        if (balance < 0) {
            result = Result("error", "Insufficient balance")
        } else {
            val storage = repo.findByIdUserIdAndIdEquipmentId(userEquipment.id.userId, userEquipment.id.equipmentId)

            // purchase again
            if (storage != null) {
                userEquipment.amount += storage.amount
                userEquipment.createdDate = storage.createdDate
            }
            repo.save(userEquipment)

            user.wallet = balance;
            userRepo.save(user)
        }

        return result
    }

    fun eat(userEquipment: UserEquipment): Result {
        var result = Result()

        val storage = repo.findByIdUserIdAndIdEquipmentId(userEquipment.id.userId, userEquipment.id.equipmentId)

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

    fun sell(userEquipment: UserEquipment): Result {
        var result = Result()

        val storage = repo.findByIdUserIdAndIdEquipmentId(userEquipment.id.userId, userEquipment.id.equipmentId)
        val equipment = equipmentRepo.getById(userEquipment.id.equipmentId)

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

                val user = userRepo.getById(userEquipment.id.userId)
                val cost = equipment.price * userEquipment.amount
                user.wallet += cost
                userRepo.save(user)
            }
        }

        return result
    }
}