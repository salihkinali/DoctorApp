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
import com.salihkinali.doctorsapp.databinding.FragmentDetailBinding


class PremiumDetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args:PremiumDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       getDoctor()
        binding.appointmentText.setOnClickListener {
            val action = PremiumDetailFragmentDirections.premiumFragmentToMeetingFragment()
            findNavController().navigate(action)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getDoctor() {
        val getDoctor = args.doctor
        binding.premiumDoctorName.text = "Dr. ${getDoctor.fullName}"
        Glide.with(binding.premiumImage)
            .load(getDoctor.image.url)
            .into(binding.premiumImage)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}