package com.arthrocode.agronomica.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "my_images")
class MyImages (

    val imageTitle : String,
    val imageDescription : String,
    val imagesAsString: String

) {
    @PrimaryKey(autoGenerate = true)
    var imageId = 0

}