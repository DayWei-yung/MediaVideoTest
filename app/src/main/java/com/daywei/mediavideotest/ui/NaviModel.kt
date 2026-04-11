package com.daywei.mediavideotest.ui

import android.app.Application
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.daywei.mediavideo.R
import com.daywei.mediavideotest.base.BaseViewModel
import com.daywei.mediavideotest.base.Event
import com.daywei.mediavideotest.consts.*
import com.daywei.mediavideotest.entity.NaviData
import com.daywei.mediavideotest.manager.ResourceManager
import com.daywei.mediavideotest.sApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NaviModel(application: Application = sApplicationContext as Application) : BaseViewModel() {

    companion object {
        private val TAG = NaviModel::class.java.simpleName
    }

    private var resManager: ResourceManager? = null

    val audioField = ObservableField<NaviData>()
    val videoField = ObservableField<NaviData>()
    val personalField = ObservableField<NaviData>()

    override fun init() {

        resManager = ResourceManager.INSTANCE
        Log.e(
            APP_TAG, "$TAG:init, audioField=${audioField.get()}, videoField=${videoField.get()}, " +
                    "personalField=${personalField.get()}"
        )
        GlobalScope.launch(Dispatchers.IO) {
            audioField.get() ?: audioField.set(
                NaviData(
                    resManager?.getBitmap(R.drawable.audio_unselect),
                    resManager?.getBitmap(R.drawable.audio_selected),
                    resManager?.getString(R.string.audio)
                )
            )
            Log.d(APP_TAG, "$TAG:audio init finish.${audioField.get()}")

            videoField.get() ?: videoField.set(
                NaviData(
                    resManager?.getBitmap(R.drawable.video_unselect),
                    resManager?.getBitmap(R.drawable.video_selected),
                    resManager?.getString(R.string.video)
                )
            )
            Log.d(APP_TAG, "$TAG:video init finish.${videoField.get()}")

            personalField.get() ?: personalField.set(
                NaviData(
                    resManager?.getBitmap(R.drawable.personal_unselect),
                    resManager?.getBitmap(R.drawable.personal_selected),
                    resManager?.getString(R.string.personal)
                )
            )
            Log.d(APP_TAG, "$TAG:personal init finish.${personalField.get()}")
        }
    }

    override fun onShow() {


    }

    override fun onHide() {

    }

    override fun onRequest(event: Event?) {
        when (event?.code) {
            REQUEST_CODE_UPDATE_NAVI -> {

            }
        }

    }

    fun whenOnChange(type: Int, callback: (field: ObservableField<NaviData>) -> Unit) {
        when (type) {
            TYPE_AUDIO -> audioField.addOnPropertyChangedCallback(object :
                Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (sender == null || !(sender is ObservableField<*>)) {
                        return
                    }
                    callback(sender as ObservableField<NaviData>)
                }
            })
            TYPE_VIDEO -> videoField.addOnPropertyChangedCallback(object :
                Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (sender == null || !(sender is ObservableField<*>)) {
                        return
                    }
                    callback(sender as ObservableField<NaviData>)
                }
            })
            TYPE_PERSONAL -> personalField.addOnPropertyChangedCallback(object :
                Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (sender == null || !(sender is ObservableField<*>)) {
                        return
                    }
                    callback(sender as ObservableField<NaviData>)
                }
            })
        }
    }

}