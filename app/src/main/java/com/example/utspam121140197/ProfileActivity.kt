package com.example.utspam121140197

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.utspam121140197.SessionData.Companion.PREF_EMAIL
import com.example.utspam121140197.SessionData.Companion.PREF_USERNAME
import com.example.utspam121140197.databinding.ActivityProfileBinding
import com.google.firebase.database.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBtn.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeBtn -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0, 0)
                    finish()
                    true
                }
                R.id.profileBtn -> {
                    true
                }
                else -> false
            }
        }
        readData(PREF_USERNAME)
    }

        private fun readData(username: String) {
            database = FirebaseDatabase.getInstance().getReference("Users")
            val emailQuery: Query = database.orderByChild("email").equalTo(PREF_EMAIL)
            database.child(username).get().addOnSuccessListener {

                if (it.exists()){
                    val email = it.child("email").value
                    val gitname = it.child("gitname").value
                    val nim = it.child("nim").value
                    binding.itemusername1.text = username
                    binding.itemEmail1.text = email.toString()
                    binding.itemgitname1.text = gitname.toString()
                    binding.itemnim1.text = nim.toString()
                } else {
                    Toast.makeText(this,"Tidak Ada User!",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener{
                Toast.makeText(this,"Gagal Memuat!",Toast.LENGTH_SHORT).show()
            }
    }
}