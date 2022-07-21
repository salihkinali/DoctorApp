package com.salihkinali.doctorsapp.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.SearchView.*
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.salihkinali.doctorsapp.adapter.DoctorAdapter
import com.salihkinali.doctorsapp.databinding.FragmentBaseBinding
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
            viewModel.searchList(textQuery,gender)
        }
        checkboxFemale.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                checkboxMale.isChecked = false
                gender = "female"
            } else {
                gender = null
            }
            viewModel.searchList(textQuery,gender)
        }
        binding.searchView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(query: CharSequence?, p1: Int, p2: Int, p3: Int) {
                textQuery = query.toString()
             viewModel.searchList(textQuery,gender)
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

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