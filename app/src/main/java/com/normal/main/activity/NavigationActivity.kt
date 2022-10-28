package com.normal.main.activity

import android.os.Bundle
import android.widget.Button
import com.normal.main.R
import com.normal.zbase.subject.BaseMVCActivity
import com.normal.zbase.utils.tools.Navigations

//Navigation
class NavigationActivity : BaseMVCActivity() {
    val navigation by lazy {
        Navigations()
    }

    override fun getLayoutResID() = R.layout.activity_navigation

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        navigation.navInit(this, R.id.mFrameLayout, R.navigation.nav_graph)
    }

    override fun initListener() {
        super.initListener()
        findViewById<Button>(R.id.btn_first).setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name", "First")
            bundle.putString("age", "12")
            //跳转到firstFragment
            navigation.navSkip(R.id.firstFragment, bundle)
        }

        findViewById<Button>(R.id.btn_second).setOnClickListener {
            navigation.navSkip(
                R.id.secondFragment,
                Bundle(),
                navigation.navOptionsExit
            )
        }
    }
}