package com.betulantep.rickandmorty.presentation.episode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.betulantep.rickandmorty.R
import com.betulantep.rickandmorty.databinding.FragmentEpisodesBinding
import com.betulantep.rickandmorty.presentation.adapter.EpisodeAdapter
import com.betulantep.rickandmorty.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodesFragment : Fragment(R.layout.fragment_episodes) {
    private val binding by viewBinding(FragmentEpisodesBinding::bind)
    private val viewModel : EpisodeViewModel by viewModels()
    private val mAdapter by lazy { EpisodeAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.episodeAdapter = mAdapter

        lifecycleScope.launch {
            viewModel.episodeList.collect{
                mAdapter.submitData(it)
            }
        }
    }
}