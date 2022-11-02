package com.normal.main.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.normal.main.R
import com.normal.zbase.subject.BaseFragment

import com.normal.zbase.subject.Navigations


/**
 * Created by zsf on 2021/1/4 13:59
 * ******************************************
 * *
 * ******************************************
 */
class FirstFragment : BaseFragment() {

    override fun getLayoutResID()=R.layout.fragment_first

    override fun initView(view: View, savedInstanceState: Bundle?) {
        super.initView(view, savedInstanceState)
        view.findViewById<Button>(R.id.btnClick).setOnClickListener {
            Navigations.navSkipFragment(view,R.id.secondFragment)
        }
    }

}