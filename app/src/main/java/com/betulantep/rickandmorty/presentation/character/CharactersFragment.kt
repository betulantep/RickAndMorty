package com.betulantep.rickandmorty.presentation.character

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.betulantep.rickandmorty.R
import com.betulantep.rickandmorty.databinding.FragmentCharactersBinding
import com.betulantep.rickandmorty.presentation.adapter.CharacterAdapter
import com.betulantep.rickandmorty.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters) {
    private val binding by viewBinding (FragmentCharactersBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var mAdapter: CharacterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCharacter.layoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        mAdapter = CharacterAdapter()
        binding.rvCharacter.adapter = mAdapter

        lifecycleScope.launchWhenStarted {
            viewModel.characterList.collectLatest {
                mAdapter.submitData(it)
            }
        }
    }
}