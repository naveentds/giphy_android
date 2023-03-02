package com.test.giphyapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.test.giphyapp.R
import com.test.giphyapp.adapter.GiphyAdapter
import com.test.giphyapp.data.model.Giph
import com.test.giphyapp.databinding.FragmentGiphsBinding
import com.test.giphyapp.ui.viewmodel.GiphyViewModel
import com.test.giphyapp.util.Resource
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "GiphyFragment"
@AndroidEntryPoint
class GiphyFragment: Fragment(R.layout.fragment_giphs), GiphyAdapter.OnItemClickListener  {
    private val viewModel: GiphyViewModel by viewModels()
    var isLoading = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentGiphsBinding.bind(view)
        val giphyAdapter = GiphyAdapter(this)

        binding.apply {
            rvGiphs.apply {
                adapter = giphyAdapter
                // setHasFixedSize(true)
            }
        }

        viewModel.getGiphs()
        viewModel.giphsLiveData.observe(viewLifecycleOwner) {
            when(it){
                is Resource.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    isLoading = false
                    it.data?.let { giphsResponse ->
                        giphyAdapter.submitList(giphsResponse.data.toList())
                    }
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    isLoading = true
                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        Log.e(TAG, "Error: $message")
                    }
                }
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onItemClick(article: Giph) {
    }

}