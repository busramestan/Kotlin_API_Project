package com.busramestan.project.view.fragment

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.busramestan.project.databinding.FragmentLoginBinding
import com.busramestan.project.model.UserLogin
import com.busramestan.project.util.MyUtil
import com.busramestan.project.view.activity.HamburgerActivity
import com.busramestan.project.viewmodel.LoginViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel : LoginViewModel

    private var email: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


       binding.emailText.setText("busra@gmail.com")
       binding.passwordText.setText("123123")

        viewModel = androidx.lifecycle.ViewModelProvider(requireActivity()).get((LoginViewModel::class.java))

        println("Email -Validation --> ${MyUtil.emailValidation("asd@asd.com")}")

        signupButton.setOnClickListener {
            val action =LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.loginButton.setOnClickListener{

            val yeni = binding.emailText.text.toString()
            val yeni2= binding.passwordText.text.toString()

           email = binding.emailText.text.toString()


            if(
                binding.passwordText.text.toString().isNotEmpty() && binding.emailText.text.toString().isNotEmpty()
            ){
                if (MyUtil.emailValidation(binding.emailText.text.toString())){
                    val user= UserLogin(binding.emailText.text.toString(),binding.passwordText.text.toString())
                    viewModel.login(user)
                } else{

                    val alertDialog= AlertDialog.Builder(context)
                    alertDialog.setTitle("Hata")
                    alertDialog.setMessage("Lütfen geçerli mail giriniz")
                    alertDialog.show()

                }
            }
            else{

                val alertDialog= AlertDialog.Builder(context)
                alertDialog.setTitle("Hata")
                alertDialog.setMessage("Lütfen tüm alanları giriniz.")
                alertDialog.show()

            }
        }

        observeLiveData()
    }

    private fun observeLiveData(){

        viewModel.loginResponse.observe(viewLifecycleOwner, Observer {

            it.token?.let { token ->

                activity?.let {
                    val preferences = it.getSharedPreferences("Project", Context.MODE_PRIVATE)
                    val editor = preferences.edit()

                    editor.putString("TOKEN",token)
                    editor.putString("Email",email)

                    editor.apply()

                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, HamburgerActivity::class.java)
                    startActivity(intent)
                }


            } ?: Toast.makeText(context, "Login Error", Toast.LENGTH_SHORT).show()



        })
    }





}