package com.arthrocode.agronomica.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64


class ConvertImage {

    companion object{

        fun convertToBitmap(imageAsString: String) : Bitmap {

            val byteArrayDecodedString = Base64.decode(imageAsString, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArrayDecodedString, 0,byteArrayDecodedString.size)
            return bitmap

        }
    }

}