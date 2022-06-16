package com.example.petheaven.vo

import java.util.*

data class NotifyVo(
    val option: String,
    val userName: String,
    val equipmentName: String,
    val equipmentUrl: String,
    val amount: Int,
    val cost: Int,
    val transactionTime: Date = Calendar.getInstance().time,
)