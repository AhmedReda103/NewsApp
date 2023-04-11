package com.example.newsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentDetailsNewsBinding
import com.example.newsapp.databinding.FragmentNewsBinding


class DetailsNewsFragment : Fragment() {


    private var _binding : FragmentDetailsNewsBinding?=null
    private val binding  get() = _binding!!

    private val args by navArgs<DetailsNewsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= FragmentDetailsNewsBinding.inflate(inflater , container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val articleItem = args.articleItem
        binding.apply {
            authorTextView.text = articleItem.author
            timeTextView.text = articleItem.publishedAt
            summaryTextView.text = articleItem.description
            titleTextview.text=articleItem.title
            mainImageView.load(articleItem.urlToImage)

        }

    }

    override fun onResume() {
        super.onResume()
        val title = activity?.findViewById<TextView>(R.id.toolbar_text_view)
        title?.text = "Details "
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}