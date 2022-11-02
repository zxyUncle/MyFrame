package com.normal.main.fragment

import android.os.Bundle
import android.view.View
import com.normal.main.R
import com.normal.main.databinding.FragmentSecondBinding
import com.normal.zbase.subject.BaseMVVMFragment
import com.normal.zbase.subject.Navigations

/**
 * Created by zsf on 2021/1/4 13:59
 * ******************************************
 * *
 * ******************************************
 */
class SecondFragment : BaseMVVMFragment<FragmentSecondBinding>() {
    override fun getLayoutResID() = R.layout.fragment_second

    override fun initView(view: View?, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)

        mDataBind.btnHome.setOnClickListener {
            Navigations.navSkipFragment(view, R.id.mvvmActivity)
        }
        mDataBind.btnFirst.setOnClickListener {
            Navigations.navSkipExist(R.id.firstFragment)
        }
    }


}