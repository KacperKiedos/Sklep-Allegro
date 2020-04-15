package com.example.allegrointernship.ui.offerDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.allegrointernship.R
import com.example.allegrointernship.databinding.FragmentOfferDetailsBinding

class OfferDetailsFragment : Fragment() {

    //get arguments passed by previous fragment
    private val args by navArgs<OfferDetailsFragmentArgs>()
    private lateinit var binding:FragmentOfferDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentOfferDetailsBinding.inflate(inflater, container, false)

        //change toolbar title to the offer title
        (activity as AppCompatActivity).supportActionBar?.title = args.offer.name

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.offer = args.offer
    }
}