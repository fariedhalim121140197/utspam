package com.example.utspam121140197

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utspam121140197.databinding.FragmentHomeBinding

class HomeFragment(private val dataList: ArrayList<DataModel.Data>) : RecyclerView.Adapter<HomeFragment.ViewHolder>() {

    inner class ViewHolder(val binding: FragmentHomeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(dataList[position]) {
                "${this.first_name} ${ this.last_name}".also { binding.itemName.text = it }
                binding.itemEmail1.text = this.email
                binding.itemId.text = this.id.toString()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<DataModel.Data>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }
}
