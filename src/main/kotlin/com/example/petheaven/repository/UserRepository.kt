package com.example.petheaven.repository

import com.example.petheaven.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByName(name : String): User?
    fun findByEmail(email : String): User?
    @Query("SELECT * FROM kelly.user WHERE id != :id AND name = :name", nativeQuery = true)
    fun findByNameAndIdIsNotNull(@Param("id") id: Long, @Param("name") name: String): User?
    @Query("SELECT * FROM kelly.user WHERE id != :id AND email = :email", nativeQuery = true)
    fun findByEmailAndIdIsNotNull(@Param("id") id: Long, @Param("email") email: String): User?
}