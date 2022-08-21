package com.betulantep.rickandmorty.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.betulantep.rickandmorty.data.entities.Character.Character
import com.betulantep.rickandmorty.databinding.CharacterRowLayoutBinding

class CharacterAdapter() :
    PagingDataAdapter<Character, CharacterAdapter.CharacterViewHolder>(DiffUtilCallBack()) {
    class CharacterViewHolder(var binding: CharacterRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            CharacterRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = getItem(position)!!
        val c = holder.binding
        val mContext = c.root.context
        c.ivCharacter.load(character.image) {
            crossfade(600)
        }
        c.tvCharacterName.text = character.name
    }


    class DiffUtilCallBack : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }

    }
}