package com.example.petheaven.model

import com.example.petheaven.vo.UserInfoVo
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
    var role: String = "Player",
    var wallet: Int = 1000,
    val pet: String,
    var createdDate: Date = Calendar.getInstance().time,
    var updatedDate: Date = Calendar.getInstance().time,

    // fetch = FetchType.LAZY 只在用到時才載入關聯的物件
    // fetch = FetchType.EAGER 在查詢時立刻載入關聯的物件
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
    val bagList: List<UserEquipment>? = null
) {
    fun convertToVo () : UserInfoVo {
        return UserInfoVo(this.id, this.name, this.email, this.wallet, this.pet)
    }
}