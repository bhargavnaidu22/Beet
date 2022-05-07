package com.gbnsolutions.beet.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gbnsolutions.beet.Adapter.LatestAdapter
import com.gbnsolutions.beet.Model.test1
import com.gbnsolutions.beet.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DBtest : AppCompatActivity() {
    lateinit var sname: TextView
    lateinit var slink: TextView
    lateinit var artist: TextView
    lateinit var catr: TextView
    lateinit var Home1: RecyclerView
    lateinit var img: ImageView
    lateinit var Manager: RecyclerView.LayoutManager
    lateinit var SL: ArrayList<test1>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_btest)
        sname = findViewById(R.id.sname)
        slink = findViewById(R.id.slink)
        artist = findViewById(R.id.artist)
        catr = findViewById(R.id.cat)
        Home1 = findViewById(R.id.m)
        img = findViewById(R.id.im)
        Manager = LinearLayoutManager(this)
        SL = arrayListOf<test1>()
        RetriveSongCategory()
        Home1.layoutManager = Manager
    }

    fun RetriveSongCategory() {
        val ref = FirebaseDatabase.getInstance().reference.child("songs")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                SL.clear()
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val song: test1? = userSnapshot.getValue(test1::class.java)
                        SL.add(song!!)
                    }
//                    Home1.adapter = LatestAdapter(this@DBtest,SL)
                }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@DBtest, "Error", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }