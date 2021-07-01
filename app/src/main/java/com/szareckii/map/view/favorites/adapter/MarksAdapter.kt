package com.szareckii.map.view.favorites.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.szareckii.map.R
import com.szareckii.map.model.data.DataModel
import kotlinx.android.synthetic.main.activity_favorites_recyclerview_item.view.*

class MarksAdapter : RecyclerView.Adapter<MarksAdapter.RecyclerItemViewHolder>() {

    private var data: MutableList<DataModel> = mutableListOf()

    // Метод передачи данных в адаптер
    fun setData(data: MutableList<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_favorites_recyclerview_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.headerMarkItemTextView.text = data.name
                itemView.descriptionMarkItemTextView.text = data.description
                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "on click: ${data.name}", Toast.LENGTH_SHORT).show()
                }
                itemView.editItemImageView.setOnClickListener { editItem() }
                itemView.removeItemImageView.setOnClickListener { removeItem() }
            }
        }
                private fun editItem() {
//                    data.add(layoutPosition, generateItem())
                    notifyItemChanged(layoutPosition)
                }

                private fun removeItem() {
                    data.removeAt(layoutPosition)
                    notifyItemRemoved(layoutPosition)
                }
    }
}