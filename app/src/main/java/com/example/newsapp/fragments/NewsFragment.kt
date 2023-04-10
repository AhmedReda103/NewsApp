package com.example.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentCategoriesBinding
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.models.Source
import com.example.newsapp.utils.NetworkResult
import com.example.newsapp.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsFragment : Fragment() {


    private var _binding : FragmentNewsBinding?=null
    private val binding  get() = _binding!!

    private val args by navArgs<NewsFragmentArgs>()

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentNewsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = args.categoryId
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.getNewsSources(category)
                mainViewModel.sources.collectLatest {
                    when(it){
                        is NetworkResult.Error -> {
                            binding.progressBar.visibility= View.INVISIBLE
                            Toast.makeText(requireContext() , it.message.toString() , Toast.LENGTH_SHORT).show()
                        }
                        is NetworkResult.Loading -> {
                            binding.progressBar.visibility= View.VISIBLE
                        }
                        is NetworkResult.Success -> {
                            binding.progressBar.visibility= View.INVISIBLE
                            setSourcesToTabLayout(it.data?.sources)
                        }
                        is NetworkResult.Unspecified -> Unit
                    }
                }
            }
        }
    }

    private fun setSourcesToTabLayout(sources: List<Source?>?) {
        sources?.forEach {
            val tab = binding.tabLayout.newTab()
            tab.text = it?.name.toString()
            tab.tag = it
            binding.tabLayout.addTab(tab)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}