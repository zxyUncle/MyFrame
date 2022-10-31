package com.normal.main.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.normal.main.R
import com.normal.zbase.subject.BaseFragment

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ThridFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ThridFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun getLayoutResID() = R.layout.fragment_thrid

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ThridFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}