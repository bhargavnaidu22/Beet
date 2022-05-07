package com.gbnsolutions.beet.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.gbnsolutions.beet.Activity.Play
import com.gbnsolutions.beet.Model.SongFetch
import com.gbnsolutions.beet.Model.test1
import com.gbnsolutions.beet.R
import com.squareup.picasso.Picasso

class LatestAdapter(val mainActivity: Context, val Latest: ArrayList<SongFetch>)
    :RecyclerView.Adapter<LatestAdapter.LatestsongsHolder>() {
        class LatestsongsHolder(view: View): RecyclerView.ViewHolder(view){
            val Sname: TextView = view.findViewById(R.id.name)
            val Simage: ImageView = view.findViewById(R.id.song)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestsongsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.latest_single_row,parent,false)
        return LatestsongsHolder(view)
    }

    override fun onBindViewHolder(holder: LatestsongsHolder, position: Int) {
        val songs: SongFetch = Latest[position]
        Picasso.get().load(songs.album_art).placeholder(R.drawable.ic_launcher_foreground).into(holder.Simage)
        holder.Sname.text = songs.songTitle
        holder.Simage.setOnClickListener {
            val i = Intent(mainActivity, Play::class.java)
            i.putExtra("t",songs.songTitle)
            i.putExtra("s",songs.songLink)
            i.putExtra("i",songs.album_art)
            i.putExtra("a",songs.artist)
            i.putExtra("c",songs.songsCategory)
            i.putExtra("pos",position)
            mainActivity.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return Latest.size
    }
}