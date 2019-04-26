package com.example.bima.movieschedule.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.bima.movieschedule.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        GlobalScope.launch {
            delay(5000)
            ctx.startActivity<MainActivity>()
            finish()
        }
    }
}
