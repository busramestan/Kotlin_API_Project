package com.busramestan.project.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.busramestan.project.R
import com.busramestan.project.adapter.ProjectAdapter
import com.busramestan.project.util.MyUtil
import com.busramestan.project.viewmodel.BaseViewModel
import com.busramestan.project.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    private lateinit var viewModel: BaseViewModel
    private lateinit var projectAdapter: ProjectAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferences = requireActivity().getSharedPreferences("Project", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN","null")

        viewModel = androidx.lifecycle.ViewModelProvider(requireActivity()).get((BaseViewModel::class.java))

        val bearerToken = "Bearer " + token

        token?.let {
            viewModel.getAllUser(bearerToken)
            println("TOKEN --> ${it}")
        } ?: println("Token null geldi.")


        recyclerView.layoutManager= LinearLayoutManager(context)

        swiperesfresh.setOnRefreshListener {
            userError.visibility=View.GONE
            userLoading.visibility=View.VISIBLE
            swiperesfresh.isRefreshing=false

            token?.let {
                viewModel.getAllUser(bearerToken)

            }
        }

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.userResponse.observe(viewLifecycleOwner, Observer {

            userLoading.visibility=View.GONE

            if (it.status) {
                // success
                println(it.data)
                recyclerView.adapter = ProjectAdapter(MyUtil.convertListToArrayList(it.data))
                Toast.makeText(context, "list success", Toast.LENGTH_SHORT).show()
            } else {
                // error
                Toast.makeText(context, "list error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.userloading.observe(viewLifecycleOwner,Observer{
            if(it){
                userLoading.visibility=View.VISIBLE

            }else{
                userLoading.visibility=View.GONE
            }
        })

        viewModel.userError.observe(viewLifecycleOwner,Observer{
            if(it){
                userError.visibility=View.VISIBLE
            }else{
                userError.visibility=View.GONE
            }

        })

    }

}