package com.hendrywinata.classin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.hendrywinata.classin.R
import com.hendrywinata.classin.data.CourseItem
import kotlinx.android.synthetic.main.course_item.view.*

class CourseListAdapter(private val courses: ArrayList<CourseItem>):
    RecyclerView.Adapter<CourseListAdapter.CourseViewHolder>() {
        inner class CourseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
            fun bind(course: CourseItem) {
                with(itemView) {
                    val semester = if (course.course_semester == "odd") "Ganjil" else "Genap"
                    course_item_name.text = "[${course.course_code}] ${course.course_name} - ${course.course_class}"
                    course_item_time.text = "$semester ${course.course_year}"
                    course_item_lecturer.text = course.lecturer_name
                    this.setOnClickListener {
                        Toast.makeText(this.context, course.course_id, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.course_item, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size
}