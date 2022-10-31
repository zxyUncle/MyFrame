package com.normal.main.fragment

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.normal.main.R
import com.normal.main.databinding.FragmentFirstBinding
import com.normal.zbase.subject.BaseFragment
import com.normal.zbase.subject.BaseMVVMActivity
import com.normal.zbase.subject.BaseMVVMFragment
import androidx.navigation.Navigation

import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.normal.zbase.utils.tools.Navigations


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