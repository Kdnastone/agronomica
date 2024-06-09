package com.arthrocode.agronomica.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.arthrocode.agronomica.model.MyImages
import com.arthrocode.agronomica.repository.MyImagesRepository


class MyImagesViewModel(application: Application): AndroidViewModel(application) {

    var repository : MyImagesRepository
    var imagesList : LiveData<List<MyImages>>

    init {
        repository = MyImagesRepository(application)
        imagesList = repository.getAllImages()
    }

    fun insert(myImages: MyImages)
}