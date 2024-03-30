package com.example.utspam121140197

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.utspam121140197.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navBtn.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.homeBtn -> {
                    true
                }
                R.id.profileBtn -> {
                    overridePendingTransition(0, 0)
                    startActivity(Intent(this, ProfileActivity::class.java))
                    finish()
                    true
                }
                else -> false
            }
        }
        setRecycler()
        loadData()
    }

    private fun viewData(data: DataModel) {
        val results = data.data
        homeFragment.setData(results)
    }

    private fun setRecycler() {
        homeFragment = HomeFragment(arrayListOf())
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.apidata.layoutManager = layoutManager
        binding.apidata.adapter = homeFragment
    }

    // add items to the list manually in our case
    private fun loadData() {
        APIService.endpoint.getData()
            .enqueue(object : Callback<DataModel> {
                override fun onResponse(p0: Call<DataModel>, p1: Response<DataModel>) {
                    if (p1.isSuccessful) {
                        viewData(p1.body()!!)
                    }
                }
                override fun onFailure(p0: Call<DataModel>, p1: Throwable) {
                    Toast.makeText(parent, "Error!", LENGTH_SHORT).show()
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
