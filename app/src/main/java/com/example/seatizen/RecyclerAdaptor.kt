package com.example.seatizen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_pattern.view.*

class RecyclerAdaptor(private var id_bus :ArrayList<String>, private var count_bus:ArrayList<String>):RecyclerView.Adapter<RecyclerAdaptor.ViewHolder>(){

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val Id : TextView = itemView.Bus_id
        val Count : TextView = itemView.Bus_count

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_pattern,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.Id.text = id_bus[position]
        holder.Count.text = count_bus[position]
    }

    override fun getItemCount(): Int {
        return id_bus.size
    }
}