package com.example.beerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.beerapp.ui.pager.PagerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PagerFragment.newInstance())
                .commitNow()
        }
    }
}