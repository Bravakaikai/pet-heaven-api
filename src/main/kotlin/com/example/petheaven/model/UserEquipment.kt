package com.example.petheaven.model

import java.io.Serializable
import java.util.*
import javax.persistence.Embeddable
import javax.persistence.EmbeddedId
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name="user_equipment")
data class UserEquipment (
    @EmbeddedId
    val id: UserEquipmentId,
    var amount: Int = 1,
    var createdDate: Date = Calendar.getInstance().time,
    var updatedDate: Date = Calendar.getInstance().time
)

@Embeddable
class UserEquipmentId : Serializable {
    val userId: Long = 0
    val equipmentId: Long = 0
}