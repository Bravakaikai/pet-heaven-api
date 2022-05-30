package com.example.petheaven.repository

import com.example.petheaven.model.UserEquipment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEquipmentRepository : JpaRepository<UserEquipment, Long> {
    fun findByIdUserId(userId : Long): List<UserEquipment?>?
    fun findByIdEquipmentId(equipmentId : Long): UserEquipment?
    fun findByIdUserIdAndIdEquipmentId(userId : Long, equipmentId : Long): UserEquipment?
}