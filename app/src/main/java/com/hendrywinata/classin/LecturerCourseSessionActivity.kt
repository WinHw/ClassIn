package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hendrywinata.classin.data.CourseItem
import kotlinx.android.synthetic.main.activity_lecturer_course_session.*

class LecturerCourseSessionActivity : AppCompatActivity() {
    private lateinit var course: CourseItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecturer_course_session)
        course = intent.getParcelableExtra("course_detail")!!
        tv_title.text = course.course_name + " " + course.course_class
        btn_back.setOnClickListener { this.finish() }
        add_session.setOnClickListener {
            startActivity(
                Intent(this@LecturerCourseSessionActivity, AddCourseSessionActivity::class.java)
                    .putExtra("course_detail", course)
            )
        }
    }
}