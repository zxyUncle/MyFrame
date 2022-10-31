package com.normal.main.activity

import android.content.Intent
import android.view.View
import com.normal.main.R
import com.normal.zbase.subject.BaseActivity

class MainActivity : BaseActivity() {

    override fun getLayoutResID()=R.layout.activity_main

    fun onClickView(view: View) {
        when (view.id) {
            //MVC
            R.id.btn_mvc -> startActivity(Intent(baseActivity, MVCActivity::class.java))
            //MVVM
            R.id.btn_mvvm -> startActivity(Intent(baseActivity, MVVMActivity::class.java))
            //MVVM
            R.id.btn_navigation -> startActivity(Intent(baseActivity, NavigationActivity::class.java))
        }
    }
}