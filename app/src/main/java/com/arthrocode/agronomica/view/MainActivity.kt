package com.arthrocode.agronomica.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthrocode.agronomica.adapter.MyImagesAdapter
import com.arthrocode.agronomica.databinding.ActivityMainBinding
import com.arthrocode.agronomica.viewmodel.MyImagesViewModel

class MainActivity : AppCompatActivity() {

    lateinit var myImagesViewModel: MyImagesViewModel
    lateinit var mainBinding: ActivityMainBinding
    lateinit var myImagesAdapter: MyImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]

        mainBinding.recyclerView.layoutManager = LinearLayoutManager(this)

        myImagesAdapter = MyImagesAdapter()
        mainBinding.recyclerView.adapter = myImagesAdapter

        myImagesViewModel.getAllImages().observe(this, Observer { images ->

            myImagesAdapter.setImage(images)
        })

        mainBinding.floatingActionButton.setOnClickListener {

            val intent = Intent(this,AddImageActivity::class.java)
            startActivity(intent)
        }
    }
}
