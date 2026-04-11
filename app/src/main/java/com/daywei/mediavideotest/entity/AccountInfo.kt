package com.daywei.mediavideotest.entity

data class AccountInfo(
    val userId: Int,
    var head: String,
    var userName: String,
    var sex: String,
    var level: Int,
    var identify: Int,
    var ipAddress: String,
    var personalType: List<String>,
    val createTime: Long,
    var signature: String,
    val state: Int
)
