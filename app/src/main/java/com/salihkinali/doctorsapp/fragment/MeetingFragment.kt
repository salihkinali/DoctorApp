package com.salihkinali.doctorsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.salihkinali.doctorsapp.R
import com.salihkinali.doctorsapp.databinding.FragmentMeetingBinding
import com.salihkinali.doctorsapp.databinding.FragmentPaymentBinding

class MeetingFragment : Fragment() {

    private var _binding: FragmentMeetingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentMeetingBinding.inflate(inflater, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}