package com.example.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.adapters.CategoriesAdapter
import com.example.newsapp.databinding.FragmentCategoriesBinding
import com.example.newsapp.models.Category
import com.example.newsapp.utils.Constants.CATEGORIES_LIST
import dagger.hilt.android.AndroidEntryPoint


class CategoriesFragment : Fragment() {

    private val categoriesAdapter by lazy {
        CategoriesAdapter(CATEGORIES_LIST)
    }

    private var _binding :FragmentCategoriesBinding ?=null
    private val binding  get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentCategoriesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.categoriesRv.adapter = categoriesAdapter

        categoriesAdapter.onClick = { category, pos ->
            val action = CategoriesFragmentDirections.actionCategoriesFragmentToNewsFragment(categoryId = category.id)
            findNavController().navigate(action)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}