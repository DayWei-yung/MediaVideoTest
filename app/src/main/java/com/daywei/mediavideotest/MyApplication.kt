package com.daywei.mediavideotest

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.daywei.mediavideotest.manager.ResourceManager

private var APPLICATION: MyApplication? = null
val sApplicationContext: Context by lazy {
    requireNotNull(APPLICATION)
}

class MyApplication : Application(), ViewModelStoreOwner {

    override fun onCreate() {
        super.onCreate()
        APPLICATION = this

        ResourceManager.INSTANCE.init(this)


    }

    override val viewModelStore: ViewModelStore
        get() = ViewModelStore()
}