package com.example.petheaven.model

import javax.persistence.*

@Entity
@Table(name="shop")
data class Shop (
    @Id
    val id: Long,
    val name: String,
    val description: String,
    val price: Int,
    val imgUrl: String,
)