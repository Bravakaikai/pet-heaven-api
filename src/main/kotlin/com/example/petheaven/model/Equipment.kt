package com.example.petheaven.model

import com.example.petheaven.vo.ShopVo
import java.util.*
import javax.persistence.*

@Entity
@Table(name="equipment")
data class Equipment (
    @Id
    @SequenceGenerator(name = "equipment_id_seq", sequenceName = "equipment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="equipment_id_seq")
    val id: Long = 0,
    val name: String,
    val description: String,
    val price: Int,
    val imgUrl: String,
    var createdDate: Date = Calendar.getInstance().time,
    var updatedDate: Date = Calendar.getInstance().time,
){
    fun convertToVo () : ShopVo {
        return ShopVo(this.id, this.name, this.description, this.price, this.imgUrl)
    }
}