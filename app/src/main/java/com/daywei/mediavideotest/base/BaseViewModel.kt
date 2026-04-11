package com.daywei.mediavideotest.base

import androidx.lifecycle.*

abstract class BaseViewModel() : ViewModel() {

    val mEvent: MutableLiveData<Event> = MutableLiveData()

    abstract fun init()
    abstract fun onShow()
    abstract fun onHide()
    abstract fun onRequest(event: Event?)
    open fun onUiCreate(lifecycleOwner: LifecycleOwner?, observer: Observer<Event?>?) {
        lifecycleOwner ?: return
        observer ?: return
        mEvent.observe(lifecycleOwner, observer)
        init()
    }

    open fun onUiDestroy(lifecycleOwner: LifecycleOwner?) {
        lifecycleOwner ?: return
        mEvent.removeObservers(lifecycleOwner)
    }

    fun notifyEvent(event: Event) {
        mEvent.postValue(event)
    }
}