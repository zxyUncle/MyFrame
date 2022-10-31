package com.normal.zbase.utils.tools

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.normal.zbase.R


/**
 * ******************************************
 * * navigation-fragment
 * ******************************************
 */
object Navigations {
    private lateinit var fragmentActivity: FragmentActivity
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var beginTransaction: FragmentTransaction
    private var frameLayout: Int = 0

    val navOptionsExit = NavOptions.Builder()
        .setEnterAnim(R.anim.in_from_left) //进入动画
        .setExitAnim(R.anim.out_to_right) //退出动画
        .build()
    val navOptionsEnter = NavOptions.Builder()
        .setEnterAnim(R.anim.in_from_right) //进入动画
        .setExitAnim(R.anim.out_to_left) //退出动画
        .build()

    /**
     * 初始化NavHostFragment
     * @param nav_graph 配置文件
     * @param frameLayout 占位FrameLayout
     */
    fun navInit(fragmentActivity: FragmentActivity, frameLayout: Int, nav_graph: Int) {
        this.fragmentActivity = fragmentActivity
        this.frameLayout = frameLayout
        navHostFragment = NavHostFragment.create(nav_graph)
        beginTransaction = fragmentActivity.supportFragmentManager.beginTransaction()
        beginTransaction
//            .replace(frameLayout, navHostFragment)
            .add(frameLayout, navHostFragment)
//            .setPrimaryNavigationFragment(navHostFragment) // 等价于 xml 中的 app:defaultNavHost="true",是否允许回退
            .commit()
    }

    /**
     * Activity中Fragment跳转
     * @param frameLayout 占位FrameLayout
     * @param bundle 传递参数
     * @param NavOptions 动画，默认是进入动画
     */
    fun navSkip(action: Int, bundle: Bundle? = Bundle(), navOptions: NavOptions = navOptionsEnter) {
        navController = Navigation.findNavController(fragmentActivity, frameLayout)
        try {
            navController.navigate(action, bundle, navOptions)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /**
     * Fragment中Fragment跳转
     * @param view getView()
     * @param frameLayout 占位FrameLayout
     * @param bundle 传递参数
     */
    fun navSkipFragment(view: View?, action: Int, bundle: Bundle = Bundle(), navOptions: NavOptions = navOptionsEnter) {
        try {
            navController = Navigation.findNavController(view!!)
            navController.navigate(action, bundle, navOptions)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //跳转到已存在的Fragment
    fun navSkipExist(fragmentId: Int, inclusive: Boolean = false) {
        navController.popBackStack(fragmentId, inclusive)
    }

    //在Activity中获取当前的Fragment对象-https://blog.csdn.net/cdc_csdn/article/details/111558525
    private fun <F : Fragment> getFragment(fragmentClass: Class<F>): F? {
        val navHostFragment =
            fragmentActivity.supportFragmentManager.fragments.first() as NavHostFragment
        navHostFragment.childFragmentManager.fragments.forEach {
            if (fragmentClass.isAssignableFrom(it.javaClass)) {
                return it as F
            }
        }
        return null
    }

    //返回上一个
    fun navigateUp() {
        navController.navigateUp()
    }
}


