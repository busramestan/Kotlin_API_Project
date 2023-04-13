package com.busramestan.project.view.fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.busramestan.project.R
import com.busramestan.project.databinding.FragmentRegisterBinding
import com.busramestan.project.model.UserRegister
import com.busramestan.project.util.MyUtil
import com.busramestan.project.viewmodel.RegisterViewModel
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {


    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!


    private lateinit var viewModel : RegisterViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel = ViewModelProviders.of(this).get(RegisterViewModel::class.java)
       // viewModel=ViewModelProviders.of(this).get(RegisterViewModel::class.java) = ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)
        viewModel = androidx.lifecycle.ViewModelProvider(requireActivity()).get((RegisterViewModel::class.java))


        var nickname =""
        val email= ""
        val password=""
        val password2 =""


            binding.signupButton.setOnClickListener {
                val nickname =binding.nickNameText.text.toString()
                val email= binding.emailText.text.toString()
                val password=binding.passwordText.text.toString()
                val password2=binding.passwordText2.text.toString()


                if(
                binding.passwordText.text.toString().isNotEmpty() &&
                binding.passwordText2.text.toString().isNotEmpty() &&
                binding.nickNameText.text.toString().isNotEmpty() &&binding.emailText.text.toString().isNotEmpty()
            ){
                if (MyUtil.emailValidation(binding.emailText.text.toString())){
                    if (binding.passwordText.text.toString()==binding.passwordText2.text.toString()){
                        val user = UserRegister(binding.nickNameText.text.toString(), binding.emailText.text.toString(),binding.passwordText.text.toString())
                        viewModel.register(user)
                    }else{

                        val alertDialog= AlertDialog.Builder(context)
                        alertDialog.setTitle("Hata")
                        alertDialog.setMessage("Lütfen aynı parola giriniz .")
                        alertDialog.show()

                    }
                }else{

                    val alertDialog=AlertDialog.Builder(context)
                    alertDialog.setTitle("Hata")
                    alertDialog.setMessage("Lütfen geçerli email giriniz.")
                    alertDialog.show()
                }
            }else {
                val alertDialog= AlertDialog.Builder(context)
                alertDialog.setTitle("Hata")
                alertDialog.setMessage("Lütfen tüm alanları doldurunuz.")
                alertDialog.show()


            }

        }

        observeLiveData()

        backImage.setOnClickListener{

            val action= RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }
    }

    private fun observeLiveData(){
        viewModel.registerResponse.observe(viewLifecycleOwner, Observer {
            if (it.status){
                // success
                Toast.makeText(context,"register success", Toast.LENGTH_SHORT).show()
            }else {
                // error
                Toast.makeText(context,"register error", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.registerError.observe(viewLifecycleOwner, Observer {
            if(it){
                Toast.makeText(context,"register error", Toast.LENGTH_SHORT).show()
            }
        })
    }




}