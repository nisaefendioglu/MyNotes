package com.task.noteapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.task.noteapp.databinding.NoteItemBinding
import com.task.noteapp.fragments.HomeFragmentDirections
import com.task.noteapp.model.NoteModel
import java.util.Random

class NoteAdapter : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    class NoteViewHolder(val itemBinding: NoteItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback =
        object : DiffUtil.ItemCallback<NoteModel>() {
            override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
                return oldItem == newItem
            }
        }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            NoteItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = differ.currentList[position]

        holder.itemBinding.title.text = currentNote.noteTitle
        holder.itemBinding.body.text = currentNote.noteBody
        holder.itemBinding.date.text = currentNote.noteDate
        holder.itemBinding.editNote.text = currentNote.noteEditInfo

        Glide.with(holder.itemView.context).load(currentNote.noteImageUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.itemBinding.img)

        val random = Random()
        val color = Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )

        holder.itemBinding.title.setTextColor(color)

        holder.itemView.setOnClickListener { mView ->
            val direction = HomeFragmentDirections
                .actionHomeFragmentToUpdateNoteFragment(currentNote)
            mView.findNavController().navigate(
                direction
            )
        }
    }

    override fun getItemCount() = differ.currentList.size
}