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

class AddImageActivity : AppCompatActivity() {

    lateinit var addImageBinding: ActivityAddImageBinding
    lateinit var activityResultLauncherForSelectedImage: ActivityResultLauncher<Intent>
    lateinit var selectImage: Bitmap
    lateinit var myImagesViewModel: MyImagesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addImageBinding = ActivityAddImageBinding.inflate(layoutInflater)
        setContentView(addImageBinding.root)

        myImagesViewModel = ViewModelProvider(this)[MyImagesViewModel::class.java]

        //registro
        registerActivityForSelectImage()

        addImageBinding.imageViewAddImage.setOnClickListener {

            if (ControlPermission.checkPermission(this)){
                //acceso a imagenes
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                // API menores a 30
                activityResultLauncherForSelectedImage.launch(intent)


            }else{
                if (Build.VERSION.SDK_INT >= 33){
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                        1
                    )
                }else{
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                }

            }
        }

        addImageBinding.buttonAdd.setOnClickListener{

            val title = addImageBinding.editTextAddTitle.text.toString()
            val description = addImageBinding.editTextAddDescription.text.toString()
            val imageAsString = ConvertImage.convertToString(selectImage)

            if (imageAsString != null){
                myImagesViewModel.insert(MyImages(title, description, imageAsString))
                finish()
            }else{
                Toast.makeText(applicationContext, "Hay un problema, por favor seleccione una nueva imagen", Toast.LENGTH_SHORT)
            }
        }

        addImageBinding.toolbarAddImage.setNavigationOnClickListener {
            finish()
        }

    }

    fun registerActivityForSelectImage(){

        activityResultLauncherForSelectedImage = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

            val resultCode = result.resultCode
            val imageData = result.data

            if (resultCode == RESULT_OK && imageData != null){

                val imageUri = imageData.data

                imageUri?.let {
                    selectImage = if (Build.VERSION.SDK_INT >=28){

                        val imageSource = ImageDecoder.createSource(this.contentResolver,it)
                        ImageDecoder.decodeBitmap(imageSource)

                    }else{
                       MediaStore.Images.Media.getBitmap(this.contentResolver,imageUri)
                    }

                    addImageBinding.imageViewAddImage.setImageBitmap(selectImage)

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

        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // API menores a 30
            activityResultLauncherForSelectedImage.launch(intent)

        }
    }
}
