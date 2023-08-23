package com.pe.edu.idat.grupo2.ui

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pe.edu.idat.grupo2.R
import com.pe.edu.idat.grupo2.databinding.ActivityLoginBinding
import java.lang.Exception

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleLauncher: ActivityResultLauncher<Intent>

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setViews()
        sharedPreferences = getSharedPreferences(SESSION_PREFERENCE, MODE_PRIVATE)
        firebaseAuth = Firebase.auth

        setViews()
        firebaseAuth = Firebase.auth
        googleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if(result.resultCode == RESULT_OK){
                val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    authFirebaseWithGoogle(account.idToken)
                }catch (e: Exception){}
            }
        }
    }

    private fun authFirebaseWithGoogle(idToken: String?) {
        val authCredential = GoogleAuthProvider.getCredential(idToken!!, null)
        firebaseAuth.signInWithCredential(authCredential)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful){
                    val user = firebaseAuth.currentUser!!
                    sharedPreferences.edit().apply(){
                        putString(EMAIL, user.email)
                            .apply()
                    }
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()

                }else{
                    Toast.makeText(this, "Ocurrio un Error", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signinWithGoogle() {
        val googleSigninOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()
        val googleClient = GoogleSignIn.getClient(this, googleSigninOptions)
        val intent = googleClient.signInIntent
        googleLauncher.launch(intent)
    }

    private fun signUpWithFirebase(email: String,password: String) {

        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if(it.isSuccessful){
                    val user = firebaseAuth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                    //opcional creo
                    Toast.makeText(this, "Login iniciado", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "El usuario no se encontro", Toast.LENGTH_SHORT).show()

                }
            }

        /*if (validateEmailPassword(email,password)){
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        Toast.makeText(this, "El usuario fue creado correctamente", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "El usuario no fue creado", Toast.LENGTH_SHORT).show()
                    }
                }
        }else{
            Toast.makeText(this, "El email y password no valido", Toast.LENGTH_SHORT).show()
        }*/
    }

    private fun setViews(){
        binding.tilEmail.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateEmailPassword(text.toString(), binding.tilPassword.editText?.text.toString())
        }

        binding.tilPassword.editText?.addTextChangedListener { text ->
            binding.btnLogin.isEnabled = validateEmailPassword(binding.tilEmail.editText?.text.toString(),text.toString())
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            signInWithFirebase(email, password)

        }
        binding.btnGoogle.setOnClickListener {
            signinWithGoogle()
        }

        binding.btnSingup.setOnClickListener {
            val email = binding.tilEmail.editText?.text.toString()
            val password = binding.tilPassword.editText?.text.toString()
            signUpWithFirebase(email, password)
        }
    }

    private fun signInWithFirebase(email: String,password: String){
        if (validateEmailPassword(email, password)){
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){task ->
                    if (task.isSuccessful){
                        val user = firebaseAuth.currentUser
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this, "Email o password incorrectos.", Toast.LENGTH_SHORT).show()
                    }

                }
        }else{
            Toast.makeText(this, "El email y password no validos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateEmailPassword(email: String, password: String) : Boolean{
        val validateEmail = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val validatePassword = password.length>6
        return validateEmail && validatePassword
    }


    companion object{
        const val SESSION_PREFERENCE: String = "SESSION_PREFERENCES"
        const val EMAIL: String = "EMAIL"
    }

}