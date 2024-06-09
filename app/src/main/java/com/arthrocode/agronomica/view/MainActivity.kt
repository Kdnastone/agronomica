package com.arthrocode.agronomica.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.arthrocode.agronomica.databinding.ActivityMainBinding
import com.arthrocode.agronomica.viewmodel.MyImagesViewModel

class MainActivity : AppCompatActivity() {

    lateinit var myImagesViewModel: MyImagesViewModel
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]

        myImagesViewModel.getAllImages().observe(this, Observer { images ->

        })

        mainBinding.floatingActionButton.setOnClickListener {

        }
    }
}
