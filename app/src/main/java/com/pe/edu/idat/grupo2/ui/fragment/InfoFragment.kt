package com.pe.edu.idat.grupo2.ui.fragment

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.FragmentInfoBinding
import com.pe.edu.idat.grupo2.ui.InitialActivity
import com.pe.edu.idat.grupo2.ui.LoginActivity

class InfoFragment : Fragment() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    lateinit var binding: FragmentInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences(
            LoginActivity.SESSION_PREFERENCE,
            AppCompatActivity.MODE_PRIVATE
        )
        firebaseAuth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCerrarSesion.setOnClickListener{
            firebaseAuth.signOut()
            sharedPreferences.edit().apply(){
                putString(LoginActivity.EMAIL,"")
                    .apply()
            }
            val intent = Intent(requireActivity(),InitialActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
    }
}