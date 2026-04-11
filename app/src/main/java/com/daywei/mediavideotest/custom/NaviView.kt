package com.daywei.mediavideotest.custom

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.daywei.mediavideo.R
import com.daywei.mediavideo.databinding.LayoutMainBottomBinding
import com.daywei.mediavideotest.base.BaseListener
import com.daywei.mediavideotest.base.BaseViewModel
import com.daywei.mediavideotest.base.Event
import com.daywei.mediavideotest.consts.APP_TAG
import com.daywei.mediavideotest.consts.EVENT_NAVI_AUDIO_LOAD
import com.daywei.mediavideotest.entity.NaviData
import com.daywei.mediavideotest.ui.MainActivity
import com.daywei.mediavideotest.ui.NaviModel

@SuppressLint("ResourceType")
class NaviView(val cxt: Context, var attr: AttributeSet?, var style: Int, var styleRes: Int) :
    ConstraintLayout(cxt, attr, style, styleRes), BaseListener {
    constructor(cxt: Context, attr: AttributeSet?, style: Int) : this(cxt, attr, style, 0)
    constructor(cxt: Context, attr: AttributeSet?) : this(cxt, attr, 0, 0)
    constructor(cxt: Context) : this(cxt, null, 0, 0)

    private var mBinding: LayoutMainBottomBinding? = null
    private var mData: NaviData? = NaviData(null, null, null)
    private var mSelected: Boolean = false

    init {
        val inflate = LayoutInflater.from(cxt).inflate(R.layout.layout_main_bottom, this, true)
        mBinding = LayoutMainBottomBinding.bind(inflate)
        val attr = cxt.obtainStyledAttributes(R.styleable.NaviView)
        mData?.title = attr.getString(R.styleable.NaviView_naviTitle)
        attr.recycle()
    }

    fun setNaviTitle(title: String?) {
        this.mData?.title = title
    }

    override fun request(event: Event?) {

    }

    override fun onEvent(event: Event?) {
        this.mData = event?.data as NaviData?
        mBinding?.txtNavi?.text = mData?.title
        if (mSelected) {
            mBinding?.imgNavi?.setImageBitmap(mData?.selected)
        } else {
            mBinding?.imgNavi?.setImageBitmap(mData?.unselect)
        }
    }

    fun setSelect(isSelected: Boolean) {
        if (isSelected == mSelected) {
            return
        }
        mSelected = isSelected
        if (mSelected) {
            mBinding?.imgNavi?.setImageBitmap(mData?.selected)
        } else {
            mBinding?.imgNavi?.setImageBitmap(mData?.unselect)
        }
    }

    fun getSelected(): Boolean {
        return mSelected;
    }
}