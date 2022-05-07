package com.gbnsolutions.beet.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gbnsolutions.beet.Activity.SongsList
import com.gbnsolutions.beet.Model.SongFetch
import com.gbnsolutions.beet.Model.test1
import com.gbnsolutions.beet.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Adapter(val context: Context, val Title: ArrayList<test1>)
    :RecyclerView.Adapter<Adapter.songsHolder>() {
    class songsHolder(view: View):RecyclerView.ViewHolder(view){
        val title: TextView = view.findViewById(R.id.title1)
        val Sname: TextView = view.findViewById(R.id.More)
        val Latestsongsrecycler: RecyclerView = view.findViewById(R.id.songs)
        lateinit var Latest: ArrayList<SongFetch>
        var Adapter: LatestAdapter?= null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): songsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter,parent,false)
        return songsHolder(view)
    }

    override fun onBindViewHolder(holder: songsHolder, position: Int) {
        val songs = Title[position]
        holder.title.text = songs.cn
        val LatestManager: RecyclerView.LayoutManager
        LatestManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        holder.Latestsongsrecycler.layoutManager = LatestManager
        holder.Sname.setOnClickListener {
            val i= Intent(context,SongsList::class.java)
            i.putExtra("title",holder.title.text.toString())
            context.startActivity(i)
        }
        holder.Latest = arrayListOf<SongFetch>()
        val refUsers = FirebaseDatabase.getInstance().reference.child("songs")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (holder.Latest as ArrayList<SongFetch>).clear()
                for (data in snapshot.children){
                    val song: SongFetch?= data.getValue(SongFetch::class.java)
                    if (song != null) {
                         if (song.songsCategory==songs.cn) {
                             (holder.Latest).add(song)
                         }
                    }
                }
                holder.Adapter = LatestAdapter(context,holder.Latest as ArrayList<SongFetch>)
                holder.Latestsongsrecycler.adapter = holder.Adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Error occured", Toast.LENGTH_SHORT).show()
            }

        })
    }
    override fun getItemCount(): Int {
        return Title.size
    }
}