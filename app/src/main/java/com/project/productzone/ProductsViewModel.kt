package com.project.productzone

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    private var _products = MutableLiveData<ArrayList<ProductsItem>>()

    val products: LiveData<ArrayList<ProductsItem>> = _products

    init {
        getProductItems()
    }

    //    _products.value = ProductService.productInstance.getProducts()[]
    private fun getProductItems() {
        viewModelScope.launch {
            try {
                _products.value = ProductService.productInstance.getProducts()
                Log.d("STATUS", "Response Received: ${_products.value}")
            } catch (e: Exception) {
                Log.d("STATUS", "Error in network call: ${e.message}")
            }
        }
    }

}