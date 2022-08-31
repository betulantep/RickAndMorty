package com.betulantep.rickandmorty.presentation.character

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.betulantep.rickandmorty.R
import com.betulantep.rickandmorty.databinding.FragmentCharactersBinding
import com.betulantep.rickandmorty.presentation.adapter.CharacterAdapter
import com.betulantep.rickandmorty.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CharactersFragment : Fragment(R.layout.fragment_characters), SearchView.OnQueryTextListener {
    private val binding by viewBinding(FragmentCharactersBinding::bind)
    private val viewModel: CharacterViewModel by viewModels()
    private val navArgs : CharactersFragmentArgs by navArgs()
    private val mAdapter by lazy { CharacterAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarCharacter)
        binding.characterAdapter = mAdapter

        //öncelik sırası yok ama belki yüklenmede çok az gecikme olabilir
        setupNavArgs()
        swipeRefresh()
        observe()
        characterListCollect()

    }

    private fun characterListCollect(){
        lifecycleScope.launch {
            viewModel.characterList.collectLatest { characterList ->
                mAdapter.submitData(characterList)
            }
        }
    }

    private fun observe(){
        viewModel.backFromBottomSheet.observe(viewLifecycleOwner) { back ->
            if (!back) {
                viewModel.getCharacters()
            } else {
                viewModel.getFilterCharacters()
            }
        }
    }

    private fun swipeRefresh(){
        binding.swipeCharacterFragment.setOnRefreshListener {
            viewModel.backFromBottomSheet.value = false
            binding.swipeCharacterFragment.isRefreshing = false
        }
    }

    private fun setupNavArgs(){
        viewModel.backFromBottomSheet.value = navArgs.backFromBottomSheet
        navArgs.status?.let { viewModel.changeValueCharacterStatus(it)}
        navArgs.species?.let { viewModel.changeValueCharacterSpecies(it)}
        navArgs.gender?.let { viewModel.changeValueCharacterGender(it)}
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_character_toolbar, menu)
        val item = menu.findItem(R.id.action_search)
        val searchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_filter -> {
                findNavController().navigate(R.id.action_charactersFragment_to_characterFilterBottomSheet)
            }
            R.id.action_refresh ->{ viewModel.backFromBottomSheet.value = false }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        //bu sorgu olmazsa karakter sildikçe veriler güncellenmiyor
        if (newText.isBlank()) { //tüm karakterler silindiğinde çalışacak
            observe() //filtrelemeden sonra search yaparsa diye observe() , filter listesinde arayacak.
                    //yoksa viewModel.getCharacters() ile ilk liste getirilebilirdi.
        } else {
            viewModel.searchCharacterName(newText)
        }
        return true
    }
}