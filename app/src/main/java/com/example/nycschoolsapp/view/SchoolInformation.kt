package com.example.nycschoolsapp.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nycschoolsapp.model.SchoolInfoResponse
import com.example.nycschools.utils.BaseFragment
import com.example.nycschoolsapp.utils.UIState
import com.example.nycschoolsapp.view.adapter.SchoolInformationAdapter
import com.example.nycschoolsapp.R
import com.example.nycschoolsapp.databinding.FragmentSchoolInformationBinding


class SchoolInformation : BaseFragment() {

    private val binding by lazy {
        FragmentSchoolInformationBinding.inflate(layoutInflater)
    }

    protected val schoolAdapter by lazy {
        SchoolInformationAdapter{
            schoolsViewModel.selectItem(it)
            findNavController().navigate(R.id.action_school_information_to_school_detail)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.rvSchoolsList.apply {
            adapter = schoolAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),LinearLayoutManager.VERTICAL,false)
        }
        // Inflate the layout for this fragment
        schoolsViewModel.schoolsInfo.observe(viewLifecycleOwner){ state ->
            when(state){
                is UIState.LOADING -> {}
                is UIState.SUCCESS<List<SchoolInfoResponse>> -> {
                    schoolAdapter.updateSchools(state.response)
                }
                is UIState.ERROR -> {
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Error occured")
                        .setMessage(state.error.localizedMessage)
                        .setPositiveButton("Retry"){dialog,_ ->
                            schoolsViewModel.getSchools()
                            dialog.dismiss()
                        }
                        .setNegativeButton("Dismiss"){dialog,_ ->
                            dialog.dismiss()

                        }
                }
            }
        }
        return binding.root
    }

}