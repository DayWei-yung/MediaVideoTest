package com.daywei.mediavideotest.manager

import android.content.Context
import com.daywei.mediavideotest.base.Event
import com.daywei.mediavideotest.consts.RESULT_FAILED
import com.daywei.mediavideotest.consts.RESULT_SUCCESS
import com.daywei.mediavideotest.entity.AccountInfo
import com.daywei.mediavideotest.sApplicationContext

object loginManager {

    const val SP_USER = "sp_user"
    const val KEY_ACCOUNT = "key_account"
    const val KEY_PASSWORD = "key_password"
    const val KEY_TOKEN = "key_token"

    suspend fun getCurrentLoginMsg(onResult: (Event) -> Unit) {
        val spLoginMsg = getSPLoginMsg()
        if (spLoginMsg == null || spLoginMsg.size < 2) {
            onResult(Event(code = RESULT_FAILED, data = null))
        } else {
            onResult(Event(code = RESULT_SUCCESS, data = spLoginMsg))
        }
    }

    suspend fun login(account: String, password: String, onResult: (Event) -> Unit) {
        if (account == null || password == null) {
            onResult(Event(code = RESULT_FAILED, null))
        }
        setSPLogin(account, password, "test_token")
        onResult(
            Event(
                code = RESULT_SUCCESS, data = AccountInfo(
                    -1, "", "name", "man", 7, 0, "unknown",
                    ArrayList(), 1L, "test_signature", 0
                )
            )
        )
    }

    fun getSPLoginMsg(): Array<String>? {
        val sharedPreferences =
            sApplicationContext.getSharedPreferences(SP_USER, Context.MODE_PRIVATE)
        val account = sharedPreferences?.getString(KEY_ACCOUNT, null)
        val password = sharedPreferences?.getString(KEY_PASSWORD, null)
        val token = sharedPreferences?.getString(KEY_TOKEN, null)
        return if (account == null || password == null) {
            null
        } else {
            Array(3) {
                account;password;token ?: ""
            }
        }
    }

    fun setSPLogin(account: String?, password: String?, token: String?) {
        if (account == null || password == null) {
            return
        }
        val sharedPreferences =
            sApplicationContext.getSharedPreferences(SP_USER, Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.apply {
            putString(KEY_ACCOUNT, account)
            putString(KEY_PASSWORD, password)
            putString(KEY_TOKEN, token)
            commit()
        }
    }
}