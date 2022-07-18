package com.salihkinali.doctorsapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import androidx.navigation.NavGraphNavigator
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.salihkinali.doctorsapp.R
import com.salihkinali.doctorsapp.adapter.DoctorAdapter
import com.salihkinali.doctorsapp.databinding.FragmentBaseBinding
import com.salihkinali.doctorsapp.model.DoctorsResponse
import com.salihkinali.doctorsapp.network.ApiService
import com.salihkinali.doctorsapp.network.DoctorApi
import com.salihkinali.doctorsapp.viewmodel.BaseViewModel
import retrofit2.Call
import retrofit2.Response
import java.util.Locale.filter
import javax.security.auth.callback.Callback


class BaseFragment : Fragment() {

    private lateinit var binding: FragmentBaseBinding
    private lateinit var viewModel: BaseViewModel
    private val adapter by lazy {
        DoctorAdapter { doctor ->
            if (doctor.userStatus == "premium") {
                val action = BaseFragmentDirections.baseFragmentToPremiumFragment(doctor)
                findNavController().navigate(action)
            } else {
                val action = BaseFragmentDirections.baseFragmentToOrdinaryDetailFragment(doctor)
                findNavController().navigate(action)
            }
        }
    }
    private var gender: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBaseBinding.inflate(inflater)
        viewModel = ViewModelProvider(this)[BaseViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getInit()
        getList()
        filter()
    }

    private fun filter() {
        val checkboxMale = binding.male
        val checkboxFemale = binding.female

        checkboxMale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkboxFemale.isChecked = false
                gender = "male"
            } else {
                gender = null
            }
        }
        checkboxFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkboxMale.isChecked = false
                gender = "female"
            } else {
                gender = null
            }
        }
    }

    private fun getInit() {
        binding.recylerView.adapter = adapter
    }

    private fun getList() {

        viewModel.doctorList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }


}