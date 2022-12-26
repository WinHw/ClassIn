package com.hendrywinata.classin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.hendrywinata.classin.data.CourseItem
import kotlinx.android.synthetic.main.activity_lecturer_course.*

class LecturerCourseActivity : AppCompatActivity() {
    private lateinit var course: CourseItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        course = intent.getParcelableExtra("course_detail")!!
        setContentView(R.layout.activity_lecturer_course)
        tv_title.text = course.course_name + " " + course.course_class
        btn_back.setOnClickListener { this.finish() }
    }
}