package com.example.petheaven.repository

import com.example.petheaven.model.Shop
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShopRepository : JpaRepository<Shop, Long> {
}