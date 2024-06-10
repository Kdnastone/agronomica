package com.arthrocode.agronomica.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arthrocode.agronomica.databinding.ImageItemBinding
import com.arthrocode.agronomica.model.MyImages
import com.arthrocode.agronomica.util.ConvertImage

class MyImagesAdapter : RecyclerView.Adapter<MyImagesAdapter.MyImagesViewHolder>() {

    var imageList : List<MyImages> =ArrayList()

    fun setImage(images: List<MyImages>){

        this.imageList = images
        notifyDataSetChanged()
    }
    class MyImagesViewHolder(val  itemBinding: ImageItemBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyImagesViewHolder {

        val view = ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyImagesViewHolder(view)
    }

    override fun getItemCount(): Int {

        return imageList.size
    }

    override fun onBindViewHolder(holder: MyImagesViewHolder, position: Int) {

        val myImage = imageList[position]
        with(holder){
            itemBinding.textViewTitle.text = myImage.imageTitle
            itemBinding.textViewDescription.text = myImage.imageDescription
            val imageAsBitmap = ConvertImage.convertToBitmap(myImage.imagesAsString)
            itemBinding.imageView.setImageBitmap(imageAsBitmap)
        }
    }


}