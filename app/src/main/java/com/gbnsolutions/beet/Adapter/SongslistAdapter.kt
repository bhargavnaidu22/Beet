package com.gbnsolutions.beet.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gbnsolutions.beet.Activity.Play
import com.gbnsolutions.beet.Activity.SongsList
import com.gbnsolutions.beet.Model.Songs
import com.gbnsolutions.beet.R

class SongslistAdapter(val songsList: SongsList,val Latest: ArrayList<Songs>)
    :RecyclerView.Adapter<SongslistAdapter.SongslistHolder>() {
        class SongslistHolder(view: View): RecyclerView.ViewHolder(view){
            val Simage1: ImageView = view.findViewById(R.id.simage1)
            val Stext1: TextView = view.findViewById(R.id.stext1)
            val Simage2: ImageView = view.findViewById(R.id.simage2)
            val Stext2: TextView = view.findViewById(R.id.stext2)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongslistHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.songs_list_single_row,parent,false)
        return SongslistHolder(view)
    }

    override fun onBindViewHolder(holder: SongslistHolder, position: Int) {
        val list = Latest[position]
        holder.Simage1.setImageResource(list.Simage)
        holder.Stext1.text = list.Sname
        holder.Simage2.setImageResource(list.Simage)
        holder.Stext2.text = list.Sname
        holder.Simage1.setOnClickListener {
            val i = Intent(songsList,Play::class.java)
            i.putExtra("t",holder.Stext1.text.toString())
            songsList.startActivity(i)
        }
        holder.Simage2.setOnClickListener {
            val i = Intent(songsList,Play::class.java)
            i.putExtra("t",holder.Stext2.text.toString())
            songsList.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return Latest.size
    }
}