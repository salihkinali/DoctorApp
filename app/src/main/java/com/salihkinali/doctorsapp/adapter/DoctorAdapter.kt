package com.salihkinali.doctorsapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.salihkinali.doctorsapp.databinding.CardviewDesignBinding
import com.salihkinali.doctorsapp.model.Doctor

class DoctorAdapter(val itemClick: (Doctor) -> Unit) :
    ListAdapter<Doctor, DoctorAdapter.ViewHolder>(DiffUtilCallBack) {

    class ViewHolder(val cardviewDesignBinding: CardviewDesignBinding) :
        RecyclerView.ViewHolder(cardviewDesignBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cardDesignRowBinding =
            CardviewDesignBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(cardDesignRowBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.cardviewDesignBinding.apply {
            doctorText.text = item.fullName
            Glide.with(cardView.context)
                .load(item.image.url)
                .into(shapeableImageView)
            root.setOnClickListener {
                itemClick.invoke(item)

            }
        }
    }

    companion object DiffUtilCallBack : DiffUtil.ItemCallback<Doctor>() {
        override fun areItemsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem.fullName == newItem.fullName
        }

        override fun areContentsTheSame(oldItem: Doctor, newItem: Doctor): Boolean {
            return oldItem == newItem
        }
    }
}

