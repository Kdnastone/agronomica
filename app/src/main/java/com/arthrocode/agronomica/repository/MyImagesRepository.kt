package com.arthrocode.agronomica.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.arthrocode.agronomica.model.MyImages
import com.arthrocode.agronomica.room.MyImagesDao
import com.arthrocode.agronomica.room.MyImagesDatabase

class MyImagesRepository(application: Application) {

    var myImagesDao : MyImagesDao
    var imagesList : LiveData<List<MyImages>>

    init {
        val database = MyImagesDatabase.getDatabaseInstance(application)
        myImagesDao = database.myImagesDao()
        imagesList = myImagesDao.getAllImages()
    }

    suspend fun insert(myImages: MyImages){
        myImagesDao.insert(myImages)
    }
    suspend fun update(myImages: MyImages){
        myImagesDao.update(myImages)
    }
    suspend fun delete(myImages: MyImages){
        myImagesDao.delete(myImages)
    }

    fun getAllImages() : LiveData<List<MyImages>>{
        return imagesList
    }

}