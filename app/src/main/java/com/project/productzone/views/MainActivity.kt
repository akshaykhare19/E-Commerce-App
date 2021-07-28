package com.project.productzone.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.productzone.R
import com.project.productzone.adapters.ProductListAdapter
import com.project.productzone.databinding.ActivityMainBinding
import com.project.productzone.extensions.Extensions.toast
import com.project.productzone.network.ProductsItem
import com.project.productzone.utils.FirebaseUtils
import com.project.productzone.viewmodels.ProductsViewModel

class MainActivity : AppCompatActivity(), ProductListAdapter.ProductItemClicked {


    private lateinit var binding: ActivityMainBinding
    private lateinit var productsViewModel: ProductsViewModel
    var data = MutableLiveData<ArrayList<ProductsItem>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productList.layoutManager = LinearLayoutManager(this)

        productsViewModel = ViewModelProvider(this).get(ProductsViewModel::class.java)


        productsViewModel.products.observe(this, {
            data.value = it
            binding.productList.adapter = ProductListAdapter(this, data)

        })
    }

    private fun logOut() {
        FirebaseUtils.firebaseAuth.signOut()
        startActivity(Intent(this, CreateAccountActivity::class.java))
        toast("Logged Out")
        finish()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.log_out_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_log_out -> logOut()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onProductClicked(product: ProductsItem) {
        val intent = Intent(this, DetailActivity::class.java)
            .putExtra("title", product.title)
            .putExtra("description", product.description)
            .putExtra("price", product.price.toString())
            .putExtra("category", product.category)
            .putExtra("id", product.id.toString())
            .putExtra("imageUrl", product.image)

        startActivity(intent)
    }
}