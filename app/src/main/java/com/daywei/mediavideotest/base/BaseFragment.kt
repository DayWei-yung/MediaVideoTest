package com.daywei.mediavideotest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

abstract class BaseFragment<T : ViewDataBinding, E : BaseViewModel> :
    Fragment(), BaseListener, Observer<Event?> {

    protected lateinit var mViewModel: E
    protected lateinit var mBinding: T

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewModel = getViewModel()
        return inflater.inflate(getResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding = DataBindingUtil.bind<T>(view)!!
        mViewModel.onUiCreate(this, this)
        init()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (hidden) {
            mViewModel.onHide()
        } else {
            mViewModel.onShow()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        release()
        mViewModel.onUiDestroy(this)
    }

    abstract fun getResource(): Int
    abstract fun getViewModel(): E
    abstract fun init()
    abstract fun release()
    override fun request(event: Event?) {
        mViewModel.onRequest(event)
    }

    override fun onChanged(value: Event?) {
        onEvent(value)
    }
}