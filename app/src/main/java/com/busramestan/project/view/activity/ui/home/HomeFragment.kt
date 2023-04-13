package com.busramestan.project.view.activity.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.busramestan.project.R
import com.busramestan.project.adapter.ProjectAdapter
import com.busramestan.project.databinding.FragmentHomeBinding
import com.busramestan.project.model.EmailModel
import com.busramestan.project.model.User
import com.busramestan.project.model.UserData
import com.busramestan.project.model.UserRegister
import com.busramestan.project.util.MyUtil
import com.busramestan.project.viewmodel.BaseViewModel
import com.busramestan.project.viewmodel.LoginViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_register.*
import kotlinx.android.synthetic.main.list_row.*
import kotlinx.android.synthetic.main.list_row.nameText

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding?= null
    private lateinit var viewModel:BaseViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


       //return inflater.inflate(R.layout.fragment_home, container, false)
       _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

       /* val textView: TextView = binding.homeText
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }   */
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        val preferences = requireActivity().getSharedPreferences("Project", Context.MODE_PRIVATE)
        val token = preferences.getString("TOKEN","null")
        val email=preferences.getString("Email","null")



        val bearerToken = "Bearer " + token

        token?.let {
            viewModel.getLogin(bearerToken, EmailModel(email!!))
            println("TOKEN --> ${it}")
        } ?: println("Token null geldi.")


        observeLiveData()
    }

    private fun observeLiveData(){

        viewModel.singleUserResponse.observe(viewLifecycleOwner, Observer {

            it.data[0].nickName?.let { nickName ->

                binding.nameText.text = nickName

            }

            binding.scoreText.text=it.data[0].score.toString()
            binding.killText.text=it.data[0].kill.toString()
            binding.deathText.text=it.data[0].death.toString()
            binding.winText.text=it.data[0].win.toString()
            binding.loseText.text=it.data[0].lose.toString()




            println("SERTAC DENEME")
            println(it.data)

        })

        viewModel.userloading.observe(viewLifecycleOwner ,Observer{
            if(it){

               // userLoading.visibility=View.VISIBLE
            }else{
               // userLoading.visibility=View.GONE
            }

        })

        viewModel.userError.observe(viewLifecycleOwner , Observer {
            if (it){

               // userError.visibility=View.VISIBLE

            }else{
               // userError.visibility=View.GONE
            }
        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    //android:contentDescription="@string/nav_header_desc"
    //app:srcCompat="@mipmap/ic_launcher_round"
    //android:text="@string/nav_header_subtitle"
}