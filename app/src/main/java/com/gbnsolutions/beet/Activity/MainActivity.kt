package com.gbnsolutions.beet.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.gbnsolutions.beet.Fragment.HomeFragment
import com.gbnsolutions.beet.Fragment.LibraryFragment
import com.gbnsolutions.beet.Fragment.SearchFragment
import com.gbnsolutions.beet.Fragment.SettingsFragment
import com.gbnsolutions.beet.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var bottomNavigationMenu: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        bottomNavigationMenu = findViewById(R.id.bottomnav)
        val home = HomeFragment()
        val search = SearchFragment()
        val library = LibraryFragment()
        val settings = SettingsFragment()
        openHome(home)
        bottomNavigationMenu.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.home->openHome(home)
                R.id.search->openHome(search)
                R.id.favourites->openHome(library)
                R.id.settings->openHome(settings)
            }
            true
        }
    }
    fun openHome(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.Container,fragment)
            commit()
        }
    }
}