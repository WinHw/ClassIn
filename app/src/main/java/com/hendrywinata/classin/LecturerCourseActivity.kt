package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.hendrywinata.classin.data.CourseItem
import kotlinx.android.synthetic.main.activity_lecturer_course.*

class LecturerCourseActivity : AppCompatActivity() {
    private lateinit var course: CourseItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lecturer_course)
        course = intent.getParcelableExtra("course_detail")!!
        tv_title.text = course.course_name + " " + course.course_class
        btn_back.setOnClickListener { this.finish() }
        students_presence.setOnClickListener {
            startActivity(
                Intent(this@LecturerCourseActivity, LecturerCourseSessionActivity::class.java)
                    .putExtra("course_detail", course)
            )
        }
    }
}