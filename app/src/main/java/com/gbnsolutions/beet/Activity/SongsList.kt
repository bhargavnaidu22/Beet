package com.gbnsolutions.beet.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gbnsolutions.beet.Adapter.SongslistAdapter
import com.gbnsolutions.beet.Model.SongFetch
import com.gbnsolutions.beet.Model.Songs
import com.gbnsolutions.beet.R

class SongsList : AppCompatActivity() {
    lateinit var List: RecyclerView
    lateinit var Adapter: SongslistAdapter
    lateinit var Manager: RecyclerView.LayoutManager
    lateinit var title: TextView
    lateinit var toolbar: Toolbar
    lateinit var back: ImageView
    private var Slist: List<SongFetch>?= null
    val Latest = arrayListOf<Songs>(
        Songs("Wolves",R.drawable.wolves),
        Songs("Pogiren",R.drawable.pogiren),
        Songs("Samajavaragamana",R.drawable.samaja),
        Songs("Shape of You",R.drawable.shapeofu),
        Songs("Shape of You",R.drawable.shapeofu),
        Songs("Star Boy",R.drawable.starboy)
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_songs_list)
        List = findViewById(R.id.songlist)
        toolbar = findViewById(R.id.listtoolbar)
        back = findViewById(R.id.back)
        setUpToolbar()
        title = findViewById(R.id.title)
        back.setOnClickListener {
            finish()
        }
//        Slist = ArrayList()

        if (intent!=null)
        {
            val t = intent.getStringExtra("title").toString()
            title.text = t
        }
        Adapter = SongslistAdapter(this,Latest)
        Manager = LinearLayoutManager(this)
        List.layoutManager = Manager
        List.adapter = Adapter
    }
    fun setUpToolbar(){
        setSupportActionBar(toolbar)
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.listmenu, menu)
        return true
    }
}