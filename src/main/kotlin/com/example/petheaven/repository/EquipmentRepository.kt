package com.example.petheaven.repository

import com.example.petheaven.model.Equipment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface EquipmentRepository : JpaRepository<Equipment, Long> {
    fun findAllByOrderByUpdatedDateDesc(): List<Equipment>
    fun findByName(name : String): Equipment?
    @Query("SELECT * FROM kelly.equipment WHERE id != :id AND name = :name", nativeQuery = true)
    fun findByNameAndIdIsNotNull(@Param("id") id: Long, @Param("name") name: String): Equipment?
}