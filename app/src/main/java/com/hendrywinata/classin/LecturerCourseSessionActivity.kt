package com.hendrywinata.classin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hendrywinata.classin.data.CourseItem
import com.hendrywinata.classin.data.PresenceDetail
import com.hendrywinata.classin.rest.RetrofitClient
import kotlinx.android.synthetic.main.activity_lecturer_course_session.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class LecturerCourseSessionActivity : AppCompatActivity() {
    private lateinit var course: CourseItem
    private val datetimeFormat = "yyyy-MM-dd HH:mm:ss"
    private val datetimeFormatter = SimpleDateFormat("EEE, d MMM\nyyyy (HH:mm)", Locale.getDefault())

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
                            tv_text.visibility = View.INVISIBLE
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
            row.apply {
                gravity = Gravity.CENTER
            }
            val presenceStatus = detail.status!!.uppercase()
//            val statusTV =
//                if (presenceStatus == "OPENED") buildButtonTo(3F, presenceStatus, detail.id.toString())
//                else buildTextView(3F, presenceStatus)
            val statusTV = buildTextView(3F, presenceStatus)
            val startTV = buildTextView(3F, datetimeFormatter.format(SimpleDateFormat(datetimeFormat).parse(detail.start)))
            val endTV = buildTextView(3F, datetimeFormatter.format(SimpleDateFormat(datetimeFormat).parse(detail.end)))
            val descTV = buildTextView(3F, detail.description.toString())
            row.addView(statusTV)
            row.addView(startTV)
            row.addView(endTV)
            row.addView(descTV)
            session_table.addView(row)
        }
    }

    private fun buildTextView(weight: Float, text: String): TextView {
        val tv = TextView(this)
        tv.textAlignment = View.TEXT_ALIGNMENT_CENTER
        tv.textSize = 12F
        tv.text = text
        tv.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.MATCH_PARENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            weight
        )
        return tv
    }

    private fun buildButtonTo(weight: Float, text: String, refID: String): Button {
        val btn = Button(this)
        btn.text = text
        btn.textSize = 12F
        btn.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.WRAP_CONTENT,
            weight
        )
        return btn
    }
}