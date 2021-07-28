package com.project.productzone.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.project.productzone.R
import com.project.productzone.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("description")
        val id = intent.getStringExtra("id")
        val category = intent.getStringExtra("category")
        val price = intent.getStringExtra("price")
        val imageUrl = intent.getStringExtra("imageUrl")

        binding.productTitle.text = title
        binding.productDesc.text = desc
        binding.productId.text = getString(R.string.product_id, id.toString())
        binding.productCategory.text = getString(R.string.category, category)
        binding.productPrice.text = getString(R.string.price, price.toString())
        Glide.with(this).load(imageUrl).into(binding.productImage)


    }

}