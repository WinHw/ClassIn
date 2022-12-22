package com.hendrywinata.classin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hendrywinata.classin.R
import com.hendrywinata.classin.data.Account
import kotlinx.android.synthetic.main.item_response.view.*

class ResponseAdapter(private val list: ArrayList<Account>): RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder>() {
    inner class ResponseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(response: Account) {
            with(itemView) {
                val text = "id: ${response.id}\nusername: ${response.username}\npassword: ${response.password}"
                response_item.text = text
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResponseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_response, parent, false)
        return ResponseViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResponseViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}