package com.salihkinali.doctorsapp.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.salihkinali.doctorsapp.R
import com.salihkinali.doctorsapp.databinding.FragmentDetailBinding
import com.salihkinali.doctorsapp.databinding.FragmentOrdinaryDetailBinding


class OrdinaryDetailFragment : Fragment() {

    private var _binding: FragmentOrdinaryDetailBinding? = null
    private val binding get() = _binding!!
    private val args: OrdinaryDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrdinaryDetailBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDoctor()
        binding.paymentText.setOnClickListener {
            val action =
                OrdinaryDetailFragmentDirections.actionOrdinaryDetailFragmentToPaymentFragment2()
            findNavController().navigate(action)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun getDoctor() {
        val getDoctor = args.doctor
        binding.singleText.text = "Dr. ${getDoctor.fullName}"
        Glide.with(binding.ordinaryImage.context)
            .load(getDoctor.image.url)
            .into(binding.ordinaryImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}