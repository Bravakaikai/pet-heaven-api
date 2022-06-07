package com.example.petheaven.vo

data class EquipmentVo (
    val id: Long = 0,
    val name: String,
    val description: String,
    val price: Int,
    val imgUrl: String,
    val userId: Long,
)