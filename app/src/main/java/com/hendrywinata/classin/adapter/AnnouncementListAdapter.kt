package com.hendrywinata.classin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hendrywinata.classin.R
import com.hendrywinata.classin.data.AnnouncementItem
import kotlinx.android.synthetic.main.announcement_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AnnouncementListAdapter(
    private val announcements: ArrayList<AnnouncementItem>,
    private val itemClickListener: (AnnouncementItem) -> Unit
): RecyclerView.Adapter<AnnouncementListAdapter.AnnouncementViewHolder>() {
    private val datetimeFormat = "yyyy-MM-dd HH:mm:ss"
    private val datetimeFormatter = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())

    inner class AnnouncementViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(announcement: AnnouncementItem, itemClickListener: (AnnouncementItem) -> Unit) {
            itemView.setOnClickListener { itemClickListener(announcement) }
            with(itemView) {
                val date = datetimeFormatter.format(SimpleDateFormat(datetimeFormat).parse(announcement.dateTime))

                announcement_list_title.text = announcement.title
                announcement_list_class.text = announcement.courseCode + " " + announcement.courseClass
                announcement_list_time.text = date
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnnouncementListAdapter.AnnouncementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.announcement_item, parent, false)
        return AnnouncementViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnnouncementListAdapter.AnnouncementViewHolder, position: Int) {
        holder.bind(announcements[position], itemClickListener)
    }

    override fun getItemCount(): Int = announcements.size
}