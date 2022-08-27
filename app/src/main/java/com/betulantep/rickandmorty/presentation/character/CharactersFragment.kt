package com.betulantep.rickandmorty.presentation.character

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.betulantep.rickandmorty.R
import com.betulantep.rickandmorty.databinding.FragmentCharactersBinding
import com.betulantep.rickandmorty.presentation.adapter.CharacterAdapter
import com.betulantep.rickandmorty.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters),SearchView.OnQueryTextListener {
    private val binding by viewBinding(FragmentCharactersBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()
    private val mAdapter by lazy { CharacterAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.characterAdapter = mAdapter

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarCharacter)
        lifecycleScope.launchWhenStarted {
            viewModel.characterList.collectLatest {
               mAdapter.submitData(it)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_character_toolbar,menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)

    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        if(newText.isBlank()){
            viewModel.getCharacters()
        }else{
            viewModel.searchCharacterName(newText)
        }

        return true
    }
}