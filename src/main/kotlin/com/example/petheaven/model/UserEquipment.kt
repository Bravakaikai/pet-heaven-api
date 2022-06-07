package com.example.petheaven.model

import java.util.*
import javax.persistence.*

@Entity
@Table(name="user_equipment")
data class UserEquipment (
    @Id
    @SequenceGenerator(name = "user_equipment_id_seq", sequenceName = "user_equipment_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="user_equipment_id_seq")
    val id: Long = 0,
    @Column(name="user_id")
    val userId: Long,
    @Column(name="equipment_id")
    val equipmentId: Long,
    var amount: Int = 1,
    var createdDate: Date = Calendar.getInstance().time,
    var updatedDate: Date = Calendar.getInstance().time,

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", referencedColumnName = "id", insertable = false, updatable = false)
    val equipment: Equipment? = null
)