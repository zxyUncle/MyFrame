package com.normal.main.activity

import android.os.Bundle
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.normal.main.R
import com.normal.main.fragment.ThridFragment
import com.normal.zbase.subject.BaseActivity
import com.normal.zbase.utils.tools.Navigations

//Navigation
class NavigationActivity : BaseActivity() {

    override fun getLayoutResID() = R.layout.activity_navigation

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        Navigations.navInit(this, R.id.fragmentContainerView, R.navigation.nav_graph)
    }

    override fun initListener() {
        super.initListener()
        findViewById<Button>(R.id.btn_first).setOnClickListener {
            Navigations.navSkip(R.id.firstFragment)
        }

        findViewById<Button>(R.id.btn_second).setOnClickListener {
            Navigations.navSkip(
                R.id.secondFragment,
                Bundle(),
                Navigations.navOptionsExit
            )
        }
    }
}