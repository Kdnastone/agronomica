package com.arthrocode.agronomica.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        myImagesAdapter = MyImagesAdapter(this)
        mainBinding.recyclerView.adapter = myImagesAdapter

        myImagesViewModel.getAllImages().observe(this, Observer { images ->

            myImagesAdapter.setImage(images)
        })

        mainBinding.floatingActionButton.setOnClickListener {

            val intent = Intent(this,AddImageActivity::class.java)
            startActivity(intent)
        }

        ItemTouchHelper (object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                TODO("Not yet implemented")
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                myImagesViewModel.delete(myImagesAdapter.returnItemAtGivenPosition(viewHolder.adapterPosition))
            }
        }).attachToRecyclerView(mainBinding.recyclerView)
    }
}
