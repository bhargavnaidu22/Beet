package com.gbnsolutions.beet.Fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gbnsolutions.beet.Adapter.Adapter
import com.gbnsolutions.beet.Model.TrackFiles
import com.gbnsolutions.beet.Model.test1
import com.gbnsolutions.beet.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class HomeFragment : Fragment() {

    lateinit var Home1: RecyclerView
    lateinit var Adapter: Adapter
    lateinit var Manager: RecyclerView.LayoutManager
    lateinit var TrackFiles: ArrayList<TrackFiles>
    lateinit var Latest: ArrayList<test1>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        Home1 = view.findViewById(R.id.home1)
        Manager = LinearLayoutManager(activity)
        Latest = arrayListOf<test1>()
        RetriveSongCategory()
        Home1.layoutManager = Manager
        TrackFiles = arrayListOf<TrackFiles>()
        poppulateFiles()
        return view
    }
    fun RetriveSongCategory(){
        val refUsers = FirebaseDatabase.getInstance().reference.child("cat")
        refUsers.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (Latest as ArrayList<test1>).clear()
                for (data in snapshot.children){
                    val song: test1?= data.getValue(test1::class.java)
                    (Latest as ArrayList<test1>).add(song!!)
                }
                Adapter = Adapter(activity as Context,Latest as ArrayList<test1>)
                Home1.adapter = Adapter
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Error occured", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun poppulateFiles(){
        val tr1: TrackFiles = TrackFiles("","",R.drawable.wolves)
        TrackFiles.add(tr1)
        val tr2: TrackFiles = TrackFiles("","",R.drawable.wolves)
        TrackFiles.add(tr2)
        val tr3: TrackFiles = TrackFiles("","",R.drawable.wolves)
        TrackFiles.add(tr3)
        val tr4: TrackFiles = TrackFiles("","",R.drawable.wolves)
        TrackFiles.add(tr4)
    }
}