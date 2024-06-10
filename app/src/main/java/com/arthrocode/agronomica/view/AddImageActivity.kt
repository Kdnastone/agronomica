package com.arthrocode.agronomica.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arthrocode.agronomica.databinding.ActivityAddImageBinding

class AddImageActivity : AppCompatActivity() {

    lateinit var addImageBinding: ActivityAddImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addImageBinding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(addImageBinding.root)

        addImageBinding.imageViewAddImage.setOnClickListener {

        }

        addImageBinding.buttonAdd.setOnClickListener{

        }

        addImageBinding.toolbarAddImage.setNavigationOnClickListener {
            finish()
        }

    }
}
