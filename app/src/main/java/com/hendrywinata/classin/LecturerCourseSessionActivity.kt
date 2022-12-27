package com.hendrywinata.classin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TableRow
import android.widget.TextView
import com.hendrywinata.classin.data.CourseItem
import com.hendrywinata.classin.data.PresenceDetail
import com.hendrywinata.classin.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_lecturer_course_session.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        RetrofitClient.instance.getPresencesByCourseID(course.course_id)
            .enqueue(object: Callback<ArrayList<PresenceDetail>> {
                override fun onResponse(
                    call: Call<ArrayList<PresenceDetail>>,
                    response: Response<ArrayList<PresenceDetail>>
                ) {
                    if (response.code() == 200) {
                        val list = response.body()
                        if (list!!.isEmpty()) {
                            tv_text.text = "No Presence Sessions Available"
                        } else {
                            buildPresenceList(list)
                        }
                    } else {
                        tv_text.text = "Fail fetching from database"
                        Log.d("GET PRESENCES FAIL ${response.code()}", response.body().toString())
                    }
                }

                override fun onFailure(call: Call<ArrayList<PresenceDetail>>, t: Throwable) {
                    tv_text.text = "Fail fetching from database..."
                    Log.d("GET PRESENCES FAIL", t.toString())
                }
            })
    }

    private fun buildPresenceList(list: ArrayList<PresenceDetail>) {
        for (detail in list) {
            val row = TableRow(this)
            val idTV = TextView(this)
            idTV.text = detail.id
            val startTV = TextView(this)
            startTV.text = detail.start
            val endTV = TextView(this)
            endTV.text = detail.start
            val descTV = TextView(this)
            descTV.text = detail.status + "\n" + detail.description
            row.addView(idTV)
            row.addView(startTV)
            row.addView(endTV)
            row.addView(descTV)
            session_table.addView(row)
        }
    }
}