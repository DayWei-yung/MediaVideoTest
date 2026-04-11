package com.daywei.mediavideotest.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer

abstract class BaseActivity<T : ViewDataBinding, E : BaseViewModel> :
    AppCompatActivity(), BaseListener, Observer<Event?> {

    protected lateinit var mViewModel: E
    protected lateinit var mBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        mBinding = DataBindingUtil.inflate(layoutInflater, getResId(), null, false)

        mViewModel = getViewModel()
        setContentView(mBinding.root)
        init()
    }

    override fun onStart() {
        super.onStart()
        mViewModel.onUiCreate(this, this)
    }

    override fun onStop() {
        super.onStop()
        mViewModel.onUiDestroy(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        release()
    }

    abstract fun getViewModel(): E
    abstract fun getResId(): Int
    abstract fun init()
    open fun release() {

    }

    override fun request(event: Event?) {
        mViewModel.onRequest(event)
    }

    override fun onChanged(value: Event?) {
        onEvent(value)
    }

    protected fun post(run: () -> Unit) {
        runOnUiThread {
            run()
        }
    }
}