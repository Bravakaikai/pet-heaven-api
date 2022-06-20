package com.example.petheaven.repository

import com.example.petheaven.model.UserEquipment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEquipmentRepository : JpaRepository<UserEquipment, Long> {
    fun findByEquipmentId(equipmentId : Long): UserEquipment?
    fun findByUserIdAndEquipmentId(userId : Long, equipmentId : Long): UserEquipment?
    fun save(storage: UserEquipment?)
}