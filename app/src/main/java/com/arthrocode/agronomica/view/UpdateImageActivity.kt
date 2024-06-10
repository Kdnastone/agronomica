package com.arthrocode.agronomica.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.arthrocode.agronomica.databinding.ActivityUpdateImageBinding

class UpdateImageActivity : AppCompatActivity() {

    lateinit var updateImageBinding: ActivityUpdateImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateImageBinding = ActivityUpdateImageBinding.inflate(layoutInflater)
        setContentView(updateImageBinding.root)

        updateImageBinding.imageViewUpdateImage.setOnClickListener {

        }

        updateImageBinding.buttonUpdate.setOnClickListener {

        }

        updateImageBinding.toolbarUpdateImage.setNavigationOnClickListener {
            finish()
        }
    }
}
