package com.example.utspam121140197

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.utspam121140197.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var sessionSaver: SessionSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = Firebase.auth
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance().getReference("user")
        sessionSaver = SessionSaver(this)

        binding.regBtn.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.loginBtn.setOnClickListener {
            val email = binding.emailText.text.toString()
            val password = binding.pwText.text.toString()

            if(email.isEmpty()) {
                binding.emailText.error = "E-Mail tidak boleh kosong!"
                binding.emailText.requestFocus()
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.emailText.error = "Input pada E-Mail tidak valid!"
                binding.emailText.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()) {
                binding.pwText.error = "E-Mail tidak boleh kosong!"
                binding.pwText.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 8) {
                binding.pwText.error = "Panjang input password terlalu pendek! (minimal 8 karakter)"
                binding.pwText.requestFocus()
                return@setOnClickListener
            }
            login(email, password)
        }
    }

    override fun onStart() {
        super.onStart()
        if (sessionSaver.getBoolean(SessionData.PREF_IS_LOGIN)) {
            goLogin()
        } else {
            setContentView(binding.root)
        }
    }

    private fun saveSession(username: String, password: String){
        sessionSaver.put(SessionData.PREF_USERNAME, username )
        sessionSaver.put(SessionData.PREF_PASSWORD, password )
        sessionSaver.put(SessionData.PREF_IS_LOGIN, true)
    }

    private fun goLogin(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }

    private fun login(email : String, password : String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    saveSession(email, password)
                    goLogin()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}