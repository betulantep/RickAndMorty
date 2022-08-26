package com.betulantep.rickandmorty.presentation.character

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.betulantep.rickandmorty.R
import com.betulantep.rickandmorty.databinding.CharacterRowLayoutBinding
import com.betulantep.rickandmorty.databinding.FragmentCharactersBinding
import com.betulantep.rickandmorty.presentation.adapter.CharacterAdapter
import com.betulantep.rickandmorty.utils.dpToPx
import com.betulantep.rickandmorty.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters) {
    private val binding by viewBinding(FragmentCharactersBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()
    private val mAdapter by lazy { CharacterAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.characterAdapter = mAdapter

        lifecycleScope.launchWhenStarted {
            viewModel.characterList.collectLatest {
               mAdapter.submitData(it)
            }
        }
    }
}