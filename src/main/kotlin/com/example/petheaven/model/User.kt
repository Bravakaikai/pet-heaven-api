package com.example.petheaven.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name="user")
data class User(
    @Id
    @SequenceGenerator(name="user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_id_seq")
    val id: Long,
    val name: String,
    val email: String,
    var password: String = "",
    val role: String = "Player",
    var wallet: Int = 1000,
    val pet: String,
    var createdDate: Date = Calendar.getInstance().time,
    var updatedDate: Date = Calendar.getInstance().time
)

data class Login (
    val email: String,
    val password: String
)

data class Bag (
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val imgUrl: String,
    val amount: Int
)