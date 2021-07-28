package com.project.productzone.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.productzone.databinding.ListItemBinding
import com.project.productzone.network.ProductsItem

class ProductListAdapter(
    private val listener: ProductItemClicked,
    private val items: LiveData<ArrayList<ProductsItem>>
) : RecyclerView.Adapter<ProductListAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val adapterLayout =
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = items.value!![position]
        holder.bindData(currentItem)

        holder.itemView.setOnClickListener {
            listener.onProductClicked(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return items.value!!.size
    }

    class ProductViewHolder(private val binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindData(item: ProductsItem) {
            val productTitle = binding.itemTitle
            productTitle.text = item.title

            val productPrice = binding.itemPrice
            productPrice.text = item.price.toString()

            val productImage = binding.itemImage
            Glide.with(this.itemView.context).load(item.image).into(productImage)
        }

    }

    interface ProductItemClicked {
        fun onProductClicked(product: ProductsItem)
    }

}