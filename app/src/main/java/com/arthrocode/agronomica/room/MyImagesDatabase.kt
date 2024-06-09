package com.arthrocode.agronomica.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arthrocode.agronomica.model.MyImages

@Database(entities = [MyImages::class], version = 1)
abstract class MyImagesDatabase : RoomDatabase() {

    abstract fun myImagesDao(): MyImagesDao
    companion object{

        @Volatile
        private var instance : MyImagesDatabase? =null

        fun getDatabaseInstance(context: Context) : MyImagesDatabase{

            synchronized(lock = this){

                if (instance == null){

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MyImagesDatabase::class.java,
                        "my_album"
                    ).build()

                }
                return instance as MyImagesDatabase

            }
        }

    }
}