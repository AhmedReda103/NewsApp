package com.example.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TableLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.newsapp.R
import com.example.newsapp.adapters.NewsAdapter
import com.example.newsapp.databinding.FragmentNewsBinding
import com.example.newsapp.models.sources.Source
import com.example.newsapp.utils.NetworkResult
import com.example.newsapp.utils.observeOnce
import com.example.newsapp.viewmodels.MainViewModel
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : Fragment() {


    private var _binding : FragmentNewsBinding?=null
    private val binding  get() = _binding!!

    private val args by navArgs<NewsFragmentArgs>()

    private val mainViewModel by viewModels<MainViewModel>()

    private val newsAdapter by lazy { NewsAdapter() }

    lateinit var category:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentNewsBinding.inflate(inflater , container , false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category = args.categoryId
        setupRecyclerView()

        readDatabase()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.news.collectLatest {
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
                            newsAdapter.differ.submitList(it.data?.articles)
                        }
                        is NetworkResult.Unspecified -> Unit
                    }
                }
            }
        }



    }

    private fun readDatabase() {
        lifecycleScope.launch {
            mainViewModel.readSources(category).observeOnce(viewLifecycleOwner){
                if(it.isNotEmpty()){
                    it.forEach {
                        if(it.category == category){
                            val sources = it.source.sources
                            addSourcesToTabLayout(sources)
                        }
                    }
                    binding.progressBar.visibility = View.INVISIBLE
                }else{
                    requestApiSources()
                }
            }
        }
    }

    private fun requestApiSources() {
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
                            addSourcesToTabLayout(it.data?.sources)
                        }
                        is NetworkResult.Unspecified -> Unit
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        binding.newsRv.adapter = newsAdapter
    }



    private fun addSourcesToTabLayout(sources: List<Source?>?) {
        sources?.forEach {
            val tab = binding.tabLayout.newTab()
            tab.text = it?.name.toString()
            tab.tag = it
            binding.tabLayout.addTab(tab)
        }
//        val source =  binding.tabLayout.getTabAt(0)?.tag as Source
//        source.id?.let {
//            mainViewModel.getNewsBySource(it)
//        }
        binding.tabLayout.addOnTabSelectedListener(object  : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val source = tab?.tag as Source
                readNewsDatabase(source.id)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                val source = tab?.tag as Source
//                source.id?.let {
//                    mainViewModel.getNewsBySource(it)
//                }
                readNewsDatabase(source.id)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

        })
    }

    private fun readNewsDatabase(id: String?) {
        mainViewModel.readNews(id!!).observeOnce(viewLifecycleOwner){
            if(it.isNotEmpty()){
                it.forEach {
                    if(it.source==id){
                        newsAdapter.differ.submitList(it.newsResponse.articles)
                    }
                }
                binding.progressBar.visibility = View.INVISIBLE
            }else{
                mainViewModel.getNewsBySource(id)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        val title = activity?.findViewById<TextView>(R.id.toolbar_text_view)
        title?.text =args.categoryId
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}