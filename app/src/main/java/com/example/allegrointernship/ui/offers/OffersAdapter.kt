package com.example.allegrointernship.ui.offers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.allegrointernship.databinding.ItemOffersListBinding
import com.example.allegrointernship.models.Offer

class OffersAdapter(private val navController: NavController) : ListAdapter<Offer, OffersAdapter.ViewHolder>(OfferDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemOffersListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offer = getItem(position)
        holder.bind(offer,createOnClickListener(offer))
    }

    private fun createOnClickListener(offer: Offer) : View.OnClickListener
    {
        //change actual fragment and pass him offer object
        return View.OnClickListener {
            navController.navigate(OffersFragmentDirections.actionOfferFragmentToOfferDetailsFragment(offer))
        }
    }

    class ViewHolder(val binding:ItemOffersListBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(offer:Offer,onClickListener: View.OnClickListener)
        {
            binding.apply {
                this.offer = offer
                this.onClickListener = onClickListener
                executePendingBindings()
            }
        }
    }

    //define DiffUtil functions needed to properly calculate differences between two lists
    class OfferDiffCallback : DiffUtil.ItemCallback<Offer>()
    {
        override fun areItemsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Offer, newItem: Offer): Boolean {
            return oldItem.equals(newItem)
        }
    }
}