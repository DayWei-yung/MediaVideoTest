package com.daywei.mediavideotest.ui.personal

import android.annotation.SuppressLint
import android.widget.Toast
import com.daywei.mediavideo.R
import com.daywei.mediavideo.databinding.LayoutPersonalBinding
import com.daywei.mediavideotest.base.BaseFragment
import com.daywei.mediavideotest.base.EVENT_CODE_DATA_PERSONAL
import com.daywei.mediavideotest.base.Event
import com.daywei.mediavideotest.consts.REQUEST_CODE_LOAD_PERSONAL

class PersonalFragment : BaseFragment<LayoutPersonalBinding, PersonalVM>() {
    override fun getResource(): Int {
        return R.layout.layout_personal
    }

    override fun getViewModel(): PersonalVM {
        return PersonalVM()
    }

    @SuppressLint("SetTextI18n")
    override fun init() {

        request(Event(code = REQUEST_CODE_LOAD_PERSONAL, data = null))

    }

    override fun release() {


    }

    override fun onEvent(event: Event?) {
        event ?: return
        when (event.code) {
            EVENT_CODE_DATA_PERSONAL -> {

                if (event.data == null) {
                    Toast.makeText(context, "请登录!", Toast.LENGTH_SHORT).also { it.show() }
                } else {
                    Toast.makeText(context, "登录成功！", Toast.LENGTH_SHORT).also { it.show() }
                }
            }

            else -> {

            }
        }

    }


}