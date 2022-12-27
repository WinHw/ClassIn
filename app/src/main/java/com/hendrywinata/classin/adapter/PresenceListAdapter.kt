package com.hendrywinata.classin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hendrywinata.classin.R
import com.hendrywinata.classin.data.PresenceItem
import kotlinx.android.synthetic.main.presence_item.view.*

class PresenceListAdapter(
    private val presences: ArrayList<PresenceItem>,
    private val itemClickListener: (PresenceItem) -> Unit
): RecyclerView.Adapter<PresenceListAdapter.PresenceViewHolder>() {
    inner class PresenceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(presence: PresenceItem, itemClickListener: (PresenceItem) -> Unit) {
            itemView.setOnClickListener { itemClickListener(presence) }
            with(itemView) {
                presence_classname.text = presence.course_name + " - " + presence.course_class
                presence_datetime.text = presence.presence_start + " - " + presence.presence_end
                presence_lecturer.text = presence.course_lecturer
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PresenceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.presence_item, parent, false)
        return PresenceViewHolder(view)
    }

    override fun onBindViewHolder(holder: PresenceViewHolder, position: Int) {
        holder.bind(presences[position], itemClickListener)
    }

    override fun getItemCount(): Int = presences.size
}