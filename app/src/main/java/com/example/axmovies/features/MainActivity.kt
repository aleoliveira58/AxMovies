package com.example.axmovies.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.axmovies.R
import com.example.axmovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}

