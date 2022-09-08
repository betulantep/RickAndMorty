package com.betulantep.rickandmorty.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.betulantep.rickandmorty.R
import com.betulantep.rickandmorty.databinding.ActivityMainBinding
import com.betulantep.rickandmorty.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        val showBottomNavigationIds = listOf(
            R.id.charactersFragment,
            R.id.episodesFragment
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (showBottomNavigationIds.contains(destination.id)) {
                binding.bottomNavigationView.visibility = View.VISIBLE
            } else {
                binding.bottomNavigationView.visibility = View.GONE
            }
        }

        NavigationUI.setupWithNavController(binding.bottomNavigationView,navController)
    }
}