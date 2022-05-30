package com.example.petheaven

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetHeavenApplication

fun main(args: Array<String>) {
	runApplication<PetHeavenApplication>(*args)
}