package com.arthrocode.agronomica.view

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.arthrocode.agronomica.databinding.ActivityAddImageBinding
import com.arthrocode.agronomica.model.MyImages
import com.arthrocode.agronomica.util.ControlPermission
import com.arthrocode.agronomica.util.ConvertImage
import com.arthrocode.agronomica.viewmodel.MyImagesViewModel
import android.util.Log

class AddImageActivity : AppCompatActivity() {

    lateinit var addImageBinding: ActivityAddImageBinding
    lateinit var activityResultLauncherForSelectedImage: ActivityResultLauncher<Intent>
    lateinit var selectImage: Bitmap
    lateinit var myImagesViewModel: MyImagesViewModel
    var control = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addImageBinding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(addImageBinding.root)

        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]

        // Registro
        registerActivityForSelectImage()

        addImageBinding.imageViewAddImage.setOnClickListener {
            if (ControlPermission.checkPermission(this)) {
                // Acceso a imágenes
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                // API menores a 30
                activityResultLauncherForSelectedImage.launch(intent)
            } else {
                if (Build.VERSION.SDK_INT >= 33) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        1
                    )
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                }
            }
        }

        addImageBinding.buttonAdd.setOnClickListener {
            Log.d("AddImageActivity", "Button Add clicked")
            val title = addImageBinding.editTextAddTitle.text.toString()
            val description = addImageBinding.editTextAddDescription.text.toString()

            if (control) {
                addImageBinding.buttonAdd.text = "En carga...Por favor espere"
                addImageBinding.buttonAdd.isEnabled = false


                Log.d("AddImageActivity", "Control is true, proceeding with image upload")
                val imageAsString = ConvertImage.convertToString(selectImage)

                if (imageAsString != null) {
                    myImagesViewModel.insert(MyImages(title, description, imageAsString))
                    control = false
                    Toast.makeText(applicationContext, "Imagen añadida con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Log.d("AddImageActivity", "Image conversion to string failed")
                    Toast.makeText(applicationContext, "Hay un problema, por favor seleccione una nueva imagen", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.d("AddImageActivity", "Control is false, no image selected")
                Toast.makeText(applicationContext, "Por favor seleccione una imagen", Toast.LENGTH_SHORT).show()
            }
        }

        addImageBinding.toolbarAddImage.setNavigationOnClickListener {
            finish()
        }
    }

    fun registerActivityForSelectImage() {
        activityResultLauncherForSelectedImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val resultCode = result.resultCode
            val imageData = result.data

            if (resultCode == RESULT_OK && imageData != null) {
                val imageUri = imageData.data
                imageUri?.let {
                    selectImage = if (Build.VERSION.SDK_INT >= 28) {
                        val imageSource = ImageDecoder.createSource(this.contentResolver, it)
                        ImageDecoder.decodeBitmap(imageSource)
                    } else {
                        MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
                    }
                    addImageBinding.imageViewAddImage.setImageBitmap(selectImage)
                    control = true
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // API menores a 30
            activityResultLauncherForSelectedImage.launch(intent)
        }
    }
}
