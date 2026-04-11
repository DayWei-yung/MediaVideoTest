package com.daywei.mediavideotest.ui.personal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daywei.mediavideotest.base.BaseViewModel
import com.daywei.mediavideotest.base.Event
import com.daywei.mediavideotest.entity.AccountInfo
import com.daywei.mediavideotest.manager.loginManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class PersonalVM() : BaseViewModel() {

    private val _account = MutableLiveData<AccountInfo>()

    val account : LiveData<AccountInfo> = _account


    override fun init() {
        MainScope().launch(Dispatchers.IO) {

            loginManager.getSPLoginMsg()?.apply {
                loginManager.login(get(0), get(1)) {
                    runBlocking (Dispatchers.Main) {
                        _account.value = it.data as AccountInfo?
                        _account.value = it.data
                    }


                }
            }

        }


        
    }

    override fun onShow() {


    }

    override fun onHide() {


    }

    override fun onRequest(event: Event?) {



    }


}