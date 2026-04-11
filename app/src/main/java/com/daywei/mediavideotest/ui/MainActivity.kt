package com.daywei.mediavideotest.ui

import android.util.Log
import android.view.View
import androidx.annotation.MainThread
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.daywei.mediavideo.BR
import com.daywei.mediavideo.R
import com.daywei.mediavideo.databinding.LayoutMainBinding
import com.daywei.mediavideotest.base.BaseActivity
import com.daywei.mediavideotest.base.Event
import com.daywei.mediavideotest.base.BaseViewModel
import com.daywei.mediavideotest.consts.*
import com.daywei.mediavideotest.entity.NaviData
import com.daywei.mediavideotest.ui.audio.AudioFragment
import com.daywei.mediavideotest.ui.personal.PersonalFragment
import com.daywei.mediavideotest.ui.video.VideoFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<LayoutMainBinding, BaseViewModel>(), View.OnClickListener {

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }

    val mFragmentCache: HashMap<Int, Fragment> = HashMap(3)

    override fun onClick(v: View?) {

        when (v?.id) {
            mBinding?.naviAudio?.id -> {
                changeNavi(type = TYPE_AUDIO)
            }

            mBinding?.naviVideo?.id -> {
                changeNavi(type = TYPE_VIDEO)
            }

            mBinding?.naviPersonal?.id -> {
                changeNavi(type = TYPE_PERSONAL)
            }
        }
    }

    override fun getViewModel(): BaseViewModel {
        return ViewModelProvider
            .AndroidViewModelFactory
            .getInstance(application)
            .create(NaviModel::class.java)
    }

    override fun getResId(): Int {
        return R.layout.layout_main
    }

    override fun onEvent(event: Event?) {
        Log.e(APP_TAG, "$TAG:onEvent, event: $event")
        MainScope().launch(Dispatchers.Main) {
            when (event?.code) {
                EVENT_NAVI_AUDIO_LOAD -> mBinding?.naviAudio?.onEvent(event)
                EVENT_NAVI_VIDEO_LOAD -> mBinding?.naviVideo?.onEvent(event)
                EVENT_NAVI_PERSONAL_LOAD -> mBinding?.naviPersonal?.onEvent(event)
            }
        }
    }

    override fun init() {
        mBinding?.setVariable(BR.navi, mViewModel)
        (mViewModel as NaviModel).whenOnChange(TYPE_AUDIO, this::onAudioChange)
        (mViewModel as NaviModel).whenOnChange(TYPE_VIDEO, this::onVideoChange)
        (mViewModel as NaviModel).whenOnChange(TYPE_PERSONAL, this::onPersonalChange)

        mBinding?.naviAudio?.setOnClickListener(this)
        mBinding?.naviVideo?.setOnClickListener(this)
        mBinding?.naviPersonal?.setOnClickListener(this)
        mBinding?.naviAudio?.setSelect(true)
    }

    override fun release() {

    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            request(Event(code = REQUEST_CODE_UPDATE_NAVI, data = null))
        }
    }

    private fun onAudioChange(field: ObservableField<*>) {
        post {
            Log.d(APP_TAG, "$TAG:onAudioChange, field:$field")
            mBinding?.naviAudio?.onEvent(Event(code = TYPE_AUDIO, data = field.get() as NaviData))
        }
    }

    private fun onVideoChange(field: ObservableField<NaviData>) {
        post {
            Log.d(APP_TAG, "$TAG:onVideoChange, field:$field")
            mBinding?.naviVideo?.onEvent(Event(code = TYPE_VIDEO, data = field.get() as NaviData))
        }
    }

    private fun onPersonalChange(field: ObservableField<NaviData>) {
        post {
            Log.d(APP_TAG, "$TAG:onVideoChange, field:$field")
            mBinding?.naviPersonal?.onEvent(
                Event(
                    code = TYPE_PERSONAL,
                    data = field.get() as NaviData
                )
            )
        }
    }

    private fun resetSelectState() {
        mBinding?.naviAudio?.setSelect(false)
        mBinding?.naviVideo?.setSelect(false)
        mBinding?.naviPersonal?.setSelect(false)
    }

    @MainThread
    private fun changeNavi(type: Int) {

        var fragment: Fragment? = null
        when (type) {
            TYPE_AUDIO -> {

                if (mBinding.naviAudio.getSelected()) {
                    return
                }
                resetSelectState()
                mBinding.naviAudio.setSelect(true)
                fragment = mFragmentCache.get(TYPE_AUDIO)
                if (fragment == null) {
                    fragment = AudioFragment()
                    mFragmentCache.put(TYPE_AUDIO, fragment)
                }
            }

            TYPE_VIDEO -> {

                if (mBinding.naviVideo.getSelected()) {
                    return
                }
                resetSelectState()
                mBinding.naviVideo.setSelect(true)
                fragment = mFragmentCache.get(TYPE_VIDEO)
                if (fragment == null) {
                    fragment = VideoFragment()
                    mFragmentCache.put(TYPE_VIDEO, fragment)
                }

            }

            TYPE_PERSONAL -> {

                if (mBinding.naviPersonal.getSelected()) {
                    return
                }
                resetSelectState()
                mBinding.naviPersonal.setSelect(true)
                fragment = mFragmentCache.get(TYPE_PERSONAL)
                if (fragment == null) {
                    fragment = PersonalFragment()
                    mFragmentCache.put(TYPE_PERSONAL, fragment)
                }

            }

            else -> {
                changeNavi(type = TYPE_AUDIO)
            }
        }
        fragment?.also {
            supportFragmentManager.beginTransaction().apply {
                replace(mBinding.mainContainer.id, fragment)
                commitAllowingStateLoss()
            }
        }
    }
}