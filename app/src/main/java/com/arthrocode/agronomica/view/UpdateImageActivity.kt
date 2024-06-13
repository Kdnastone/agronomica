package com.arthrocode.agronomica.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arthrocode.agronomica.databinding.ActivityUpdateImageBinding
import com.arthrocode.agronomica.model.MyImages
import com.arthrocode.agronomica.util.ConvertImage
import com.arthrocode.agronomica.viewmodel.MyImagesViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UpdateImageActivity : AppCompatActivity() {

    lateinit var updateImageBinding: ActivityUpdateImageBinding
    var id = -1
    lateinit var viewModel: MyImagesViewModel
    var imageAsString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateImageBinding = ActivityUpdateImageBinding.inflate(layoutInflater)
        setContentView(updateImageBinding.root)

        viewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]
        getAndSetData()

        updateImageBinding.imageViewUpdateImage.setOnClickListener {
            // Lógica para actualizar la imagen (opcional, si deseas cambiar la imagen)
        }

        updateImageBinding.buttonUpdate.setOnClickListener {
            updateImage()
        }

        updateImageBinding.toolbarUpdateImage.setNavigationOnClickListener {
            finish()
        }
    }

    private fun getAndSetData(){
        id= intent.getIntExtra("id", -1)
        if (id != -1){
            CoroutineScope(Dispatchers.IO).launch {
                val myImage = viewModel.getItemById(id)

                withContext(Dispatchers.Main){
                    updateImageBinding.editTextUpdateTitle.setText(myImage.imageTitle)
                    updateImageBinding.editTextUpdateDescription.setText(myImage.imageDescription)
                    imageAsString = myImage.imagesAsString
                    val imageAsBitmap = ConvertImage.convertToBitmap(imageAsString)
                    updateImageBinding.imageViewUpdateImage.setImageBitmap(imageAsBitmap)
                }
            }
        }
    }

    private fun updateImage() {
        val title = updateImageBinding.editTextUpdateTitle.text.toString()
        val description = updateImageBinding.editTextUpdateDescription.text.toString()

        if (id != -1) {
            val updatedImage = MyImages(title, description, imageAsString)
            updatedImage.imageId = id
            viewModel.update(updatedImage)
            finish()
        }
    }
}
