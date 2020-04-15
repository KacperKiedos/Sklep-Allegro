package com.example.allegrointernship.ui.offers

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.allegrointernship.R
import com.example.allegrointernship.databinding.FragmentOffersBinding
import com.example.allegrointernship.models.Resource
import com.example.allegrointernship.util.ForceRefreshCallback

class OffersFragment : Fragment() {

    private val viewModel: OffersViewModel by viewModels()
    private lateinit var binding:FragmentOffersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOffersBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner

        //creating and passing adapter to the recycler view
        val adapter = OffersAdapter(findNavController())
        binding.offersList.adapter = adapter

        binding.offers = viewModel.offers
        binding.forceRefreshCallback = object: ForceRefreshCallback {
            override fun forceRefresh() {
                viewModel.forceRefresh()
            }
        }

        //create observers for ViewModel properties
        subscribeUI(adapter)
    }

    private fun subscribeUI(adapter:OffersAdapter)
    {
        viewModel.offers.observe(viewLifecycleOwner, Observer {list ->
            //check if swipe to refresh was invoked without internet connection
            if(!list.message.isNullOrBlank()
                    && list.message.startsWith(getString(R.string.no_internet_error_message_start))
                    && binding.offersSwipeRefreshLayout.isRefreshing)
                showDialog(getString(R.string.internet_problem_title), getString(R.string.internet_problem_message))

            //hide swipe-to-refresh loading icon after refresh
            if(list.status == Resource.Status.SUCCESS || list.status == Resource.Status.ERROR)
                binding.offersSwipeRefreshLayout.isRefreshing = false

            //pass data given by ViewModel to the list adapter
            adapter.submitList(list?.data)
        })


    }

    private fun showDialog(title:String,message:String)
    {
        AlertDialog.Builder(context as Activity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok)){dialog, which ->  dialog.cancel()}
            .show()
    }

}