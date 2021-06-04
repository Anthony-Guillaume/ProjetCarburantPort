package com.example.bateaubreton.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bateaubreton.databinding.ItemPortLocationBinding
import com.example.bateaubreton.view.utils.PortLocation

class PortLocationAdapter(
    private val dataList: List<PortLocation>,
    private val listenerOnEditClick: (Int) -> Unit)
    : RecyclerView.Adapter<PortLocationAdapter.ViewHolder>()
{
    companion object {
        const val TAG: String = "PortLocationAdapter"
    }

    class ViewHolder(val binding: ItemPortLocationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(
            ItemPortLocationBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val item: PortLocation = dataList[position]
        holder.binding.apply {
            textViewLocation.text = item.port.name
            textViewPriceDiesel.text = String.format("%.2f", item.fuel.diesel)
            textViewPriceSp98.text = String.format("%.2f", item.fuel.sp98)
            textViewDistance.text = String.format("%.1f", item.distance)
            imageViewEditPrice.setOnClickListener { listenerOnEditClick(holder.bindingAdapterPosition) }
        }
    }

    override fun getItemCount(): Int = dataList.size
}