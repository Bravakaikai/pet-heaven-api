package com.example.petheaven.model

data class Result(
    var status: String = "success",
    var message: Any? = ""
) {
    companion object {
        fun errorResult(): Result {
            return Result("error", "Unexpected error")
        }
    }
}
