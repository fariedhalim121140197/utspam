package com.example.utspam121140197

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.utspam121140197.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var storage: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        database = FirebaseDatabase.getInstance().getReference("user")
        var uri: Uri? = null

        binding.regBtn.setOnClickListener {

            val email = binding.emailText.text.toString()
            val password = binding.pwText.text.toString()
            val nim = binding.nim.text.toString()
            val gitname = binding.gitname.text.toString()
            val username = binding.username.text.toString()

            updateData(username, email, gitname, nim, password)
        }

    }

    private fun updateData(username: String, email: String, gitname: String, nim: String, password: String) {

        database = FirebaseDatabase.getInstance().getReference("Users")
        val user = mapOf<String, String>(
            "email" to email,
            "gitname" to gitname,
            "nim" to nim,
            "password" to password
        )

        auth.createUserWithEmailAndPassword(email, password)
        database.child(username).updateChildren(user).addOnSuccessListener {

            binding.username.text.clear()
            binding.emailText.text.clear()
            binding.gitname.text.clear()
            binding.nim.text.clear()
            binding.pwText.text.clear()
            Toast.makeText(this, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()

        }.addOnFailureListener {
            Toast.makeText(this, "Registrasi Gagal!", Toast.LENGTH_SHORT).show()
        }
    }
}
