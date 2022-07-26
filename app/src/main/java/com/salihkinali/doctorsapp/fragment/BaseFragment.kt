package com.salihkinali.doctorsapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.salihkinali.doctorsapp.adapter.DoctorAdapter
import com.salihkinali.doctorsapp.databinding.FragmentBaseBinding
import com.salihkinali.doctorsapp.util.NetworkResult
import com.salihkinali.doctorsapp.viewmodel.BaseViewModel


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
    private var textQuery: String? = null

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
            viewModel.searchList(textQuery, gender)
        }
        checkboxFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkboxMale.isChecked = false
                gender = "female"
            } else {
                gender = null
            }
            viewModel.searchList(textQuery, gender)
        }
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                textQuery = newText
                viewModel.searchList(textQuery, gender)
                return true
            }
        })
    }

    private fun getInit() {
        binding.recylerView.adapter = adapter
    }

    private fun getList() {
        viewModel.status.observe(viewLifecycleOwner) { networkResult ->

            when (networkResult) {

                is NetworkResult.Success -> {
                    viewModel.doctorList.observe(viewLifecycleOwner) { doctorList ->
                        if (doctorList.isNullOrEmpty()) {
                            binding.userNotFoundImage.visibility = View.VISIBLE
                            binding.userNotFoundText.visibility = View.VISIBLE
                            binding.recylerView.visibility = View.GONE
                        } else {
                            binding.userNotFoundImage.visibility = View.GONE
                            binding.userNotFoundText.visibility = View.GONE
                            binding.recylerView.visibility = View.VISIBLE
                            adapter.submitList(doctorList)
                        }
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(
                        requireContext(),
                        networkResult.exception.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                is NetworkResult.Failure -> {
                    Toast.makeText(
                        requireContext(),
                        networkResult.throwable.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }
}