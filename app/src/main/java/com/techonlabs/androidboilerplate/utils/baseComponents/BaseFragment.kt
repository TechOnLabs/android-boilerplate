package com.techonlabs.androidboilerplate.utils.baseComponents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.techonlabs.androidboilerplate.utils.recyclerView.OnRecyclerItemClickListener


abstract class BaseFragment : Fragment(), OnRecyclerItemClickListener {

    abstract val vm: BaseViewModel?
    protected lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (view != null) {
            val parent = view!!.parent as ViewGroup
            parent.removeView(view)
        }
        return onCreateBaseView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    abstract fun onCreateBaseView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

}