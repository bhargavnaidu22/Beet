package com.gbnsolutions.beet.Activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.gbnsolutions.beet.R

class SplashScreen : AppCompatActivity() {
    lateinit var splas: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
//        splas = findViewById(R.id.ss)
//        splas.alpha = 0f
//        splas.animate().setDuration(500).alpha(1f).withEndAction{
//            val i = Intent(this@SplashScreen, MainActivity::class.java)
//            startActivity(i)
//            finish()
//        }
        // setSkipPermissionRequest(true);
        //  setWelcomePageDisallowed(true);
        Handler().postDelayed({ gotoMainActivity() }, 300)
    }
    private fun gotoMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}