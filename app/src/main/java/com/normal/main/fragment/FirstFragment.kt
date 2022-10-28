package com.normal.main.fragment

import com.normal.main.R
import com.normal.main.databinding.FragmentFirstBinding
import com.normal.zbase.subject.BaseMVVMActivity
import com.normal.zbase.subject.BaseMVVMFragment

/**
 * Created by zsf on 2021/1/4 13:59
 * ******************************************
 * *
 * ******************************************
 */
class FirstFragment : BaseMVVMFragment<FragmentFirstBinding>() {

    override fun getLayoutResID()=R.layout.fragment_first

}