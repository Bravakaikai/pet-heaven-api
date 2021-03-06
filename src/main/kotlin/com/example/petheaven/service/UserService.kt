package com.example.petheaven.service

import com.example.petheaven.vo.LoginVo
import com.example.petheaven.model.Result
import com.example.petheaven.model.User
import com.example.petheaven.repository.UserRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class UserService(val repo: UserRepository) {
    fun getUserList(): Result {
        return Result(
            message = repo.findAll()
        )
    }

    fun getInfo(id: Long): Result {
        val user: User = repo.getById(id)
        return Result(message = user.convertToVo())
    }

    @Transactional
    fun signup(user: User): Result {
        var result = Result()

        result = checkDuplicate(result, user)

        // no error
        if (result.status != "error") {
            repo.save(user)

            val currentUser = repo.findByEmail(user.email)
            result.message = currentUser?.id
        }
        return result
    }

    fun login(loginVo: LoginVo): Result {
        var result = Result()

        val currentUser = repo.findByEmail(loginVo.email)

        if (currentUser != null) {
            if (currentUser.password == loginVo.password) {
                result.message = currentUser.id
            } else {
                result = Result("error", "Incorrect email or password")
            }
        } else {
            result.status = "not found"
        }

        return result
    }

    fun editInfo(user: User): Result {
        var result = Result()

        if (repo.existsById(user.id)) {
            result = checkDuplicate(result, user)

            // no error
            if (result.status != "error") {
                val currentUser = repo.getById(user.id)

                if (user.password.isEmpty()) {
                    user.password = currentUser.password
                }

                user.role = currentUser.role
                user.wallet = currentUser.wallet
                user.createdDate = currentUser.createdDate
                repo.save(user)
            }
        } else {
            result.status = "not found"
        }

        return result
    }

    fun checkIsAdmin(id: Long): Result {
        val user: User = repo.getById(id)
        return Result(
            message = (user.role == "Admin")
        )
    }

    // check name & email is duplicate
    fun checkDuplicate(result: Result, user: User): Result {
        val errMsg = mutableMapOf<String, String>()

        val nameIsExist: User?
        val emailIsExist: User?

        // editInfo
        if (user.id != 0L) {
            nameIsExist = repo.findByNameAndIdIsNotNull(user.id, user.name)
            emailIsExist = repo.findByEmailAndIdIsNotNull(user.id, user.email)
        } else {
            // sign up
            nameIsExist = repo.findByName(user.name)
            emailIsExist = repo.findByEmail(user.email)
        }

        if (nameIsExist != null) {
            errMsg["name"] = "Duplicate username"
        }
        if (emailIsExist != null) {
            errMsg["email"] = "Duplicate email"
        }

        if (errMsg.isNotEmpty()) {
            result.status = "error"
            result.message = errMsg
        }

        return result
    }
}

