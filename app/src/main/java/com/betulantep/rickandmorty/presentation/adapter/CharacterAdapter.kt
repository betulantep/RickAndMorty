package com.betulantep.rickandmorty.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.betulantep.rickandmorty.databinding.CharacterRowLayoutBinding
import com.betulantep.rickandmorty.domain.uimodel.CharacterUIModel

class CharacterAdapter() :
    PagingDataAdapter<CharacterUIModel, CharacterAdapter.CharacterViewHolder>(DiffUtilCallBack()) {
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


    class DiffUtilCallBack : DiffUtil.ItemCallback<CharacterUIModel>() {
        override fun areItemsTheSame(oldItem: CharacterUIModel, newItem: CharacterUIModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterUIModel, newItem: CharacterUIModel): Boolean {
            return oldItem == newItem
        }

    }
}